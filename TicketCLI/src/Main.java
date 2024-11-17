import actors.Customer;
import actors.Vendor;
import systemConfiguration.SystemConfig;
import ticketPool.TicketPool;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private enum SystemState { STOPPED, RUNNING }
    private static SystemState currentState = SystemState.STOPPED;
    private static SystemConfig config = new SystemConfig();
    private static TicketPool ticketPool;
    final private static List<Thread> vendorThreads = new ArrayList<>();
    final private static List<Thread> customerThreads = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hi, Enter the configuration parameters: ");
        promptForInput();
        sysControl();
    }

    private static void promptForInput() {
        Scanner configScanner = new Scanner(System.in);

        System.out.print("Enter Maximum Ticket Capacity: ");
        config.setMaxTicketCapacity(config.getValidatedInput(configScanner, "Maximum Ticket Capacity"));

        while (true) {
            System.out.print("Enter Total Tickets: ");
            try {
                config.setTotalTickets(config.getValidatedInput(configScanner, "Total Tickets"));
                break; // Exit loop if input is valid
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter Ticket Release Rate: ");
        config.setTicketReleaseRate(config.getValidatedInput(configScanner, "Ticket Release Rate"));

        System.out.print("Enter Customer Retrieval Rate: ");
        config.setCustomerRetrievalRate(config.getValidatedInput(configScanner, "Customer Retrieval Rate"));

        System.out.print("Enter Vendor count: ");
        config.setVendor(config.getValidatedInput(configScanner, "vendor count"));

        System.out.print("Enter customer count: ");
        config.setCustomer(config.getValidatedInput(configScanner, "customer count"));

        config.saveToFile("resources/configuration.json");

    }

    private static void sysControl() {
        System.out.println("""
                Follow the system controls:\s
                | Enter command '1' to load system configurations |\s
                | Enter command '2' to start the buying and selling |\s
                | Enter command '4' to stop the buying and selling |\s
                | Enter command '5' to stop the running system |\s
                """);
        Scanner controlScanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter command: ");
                int command = controlScanner.nextInt();

                switch (command) {
                    case 1:
                        loadConfig();
                        break;
                    case 2:
                        if (currentState == SystemState.RUNNING) {
                            System.out.println("System is already running.");
                        } else {
                            System.out.println("Starting buying and selling...");
                            startThreads();
                            currentState = SystemState.RUNNING;
                        }
                        break;
                    case 4:
                        if (currentState == SystemState.STOPPED) {
                            System.out.println("System is already stopped.");
                        } else {
                            System.out.println("Stopping buying and selling...");
                            stopThreads();
                            currentState = SystemState.STOPPED;
                        }
                        break;
                    case 5:
                        System.out.println("Stopping the system...");
                        System.exit(0);
                    default:
                        System.out.println("Invalid command. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid command. Please enter a valid integer.");
                controlScanner.nextLine(); // Clear invalid input
            }
        }
    }

    private static void startThreads() {
        if (ticketPool == null) {
            ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());
        }

        try {
            for (int i = 0; i < config.getVendor(); i++) {
                Thread vendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate()));
                vendorThreads.add(vendor);
                vendor.start();
            }

            for (int i = 0; i < config.getCustomer(); i++) {
                Thread customer = new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate()));
                customerThreads.add(customer);
                customer.start();
            }
            System.out.println("Threads started.");
        } catch (Exception e) {
            System.out.println("Error starting threads: " + e.getMessage());
        }
    }


    private static void stopThreads() {
        try {
            vendorThreads.forEach(thread -> {
                if (thread.isAlive()) {
                    thread.interrupt();
                }
            });
            customerThreads.forEach(thread -> {
                if (thread.isAlive()) {
                    thread.interrupt();
                }
            });
            vendorThreads.clear();
            customerThreads.clear();
            System.out.println("Threads stopped.");
        } catch (Exception e) {
            System.out.println("Error stopping threads: " + e.getMessage());
        }
    }

    private static void loadConfig() {
        config = SystemConfig.loadFromFile("resources/configuration.json");
        if (config != null) {
            System.out.println("Configuration loaded successfully:");
            System.out.println("Max Ticket Capacity: " + config.getMaxTicketCapacity());
            System.out.println("Total Tickets: " + config.getTotalTickets());
            System.out.println("Ticket Release Rate: " + config.getTicketReleaseRate());
            System.out.println("Customer Retrieval Rate: " + config.getCustomerRetrievalRate());
            System.out.println("vendor count: " + config.getVendor());
            System.out.println("Customer count: " + config.getCustomer());
        }
    }
}
