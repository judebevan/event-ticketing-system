import actors.Customer;
import actors.Vendor;
import systemConfiguration.SystemConfig;
import ticketPool.TicketPool;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
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

        System.out.println("Enter Maximum Ticket Capacity: ");
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
                | Enter command '200' to load system configurations |\s
                | Enter command '201' to start the buying and selling |\s
                | Enter command '400' to stop the buying and selling |\s
                | Enter command '500' to stop the running system |\s
                """);
        Scanner controlScanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("Enter command: ");
                int command = controlScanner.nextInt();

                switch (command) {
                    case 200:
                        loadConfig();
                        break;
                    case 201:
                        System.out.println("Starting buying and selling...");
                        startThreads();
                        break;
                    case 400:
                        System.out.println("Stopping buying and selling...");
                        stopThreads();
                        break;
                    case 500:
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
    }

    private static void stopThreads() {
        vendorThreads.forEach(Thread::interrupt);
        customerThreads.forEach(Thread::interrupt);
        vendorThreads.clear();
        customerThreads.clear();
        System.out.println("Threads stopped.");
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
