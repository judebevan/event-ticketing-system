package ticketing;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.InputMismatchException;

public class Main {
    private static Configuration config;
    public static AtomicBoolean isRunning = new AtomicBoolean(false);  // To track start/stop status

    public static void main(String[] args) {
        loadConfig();
        promptForInput();

        // TicketPool to hold the tickets
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        // CLI Control for start/stop actions
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command: (start/stop/exit)");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "start":
                    if (isRunning.compareAndSet(false, true)) {
                        startThreads(ticketPool);
                    } else {
                        System.out.println("System is already running.");
                    }
                    break;
                case "stop":
                    if (isRunning.compareAndSet(true, false)) {
                        stopThreads();
                    } else {
                        System.out.println("System is already stopped.");
                    }
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command. Please enter 'start', 'stop', or 'exit'.");
            }
        }
    }

    private static void startThreads(TicketPool ticketPool) {
        // Start Vendor and Customer Threads
        for (int i = 0; i < config.getVendorCount(); i++) {
            new Thread(new Vendor(ticketPool, config.getTicketReleaseRate())).start();
        }
        for (int i = 0; i < config.getCustomerCount(); i++) {
            new Thread(new Customer(ticketPool, config.getCustomerRetrievalRate())).start();
        }
    }

    private static void stopThreads() {
        // Set the running flag to false to stop threads
        System.out.println("Stopping all ticket handling operations...");
        // You could implement thread interruption or use other methods to stop vendor/customer threads
    }

    private static void promptForInput() {
        Scanner scanner = new Scanner(System.in);

        // Validate integer input for max ticket capacity
        config.setMaxTicketCapacity(getValidIntInput(scanner, "Enter max ticket capacity:"));

        // Validate integer input for total tickets
        config.setTotalTickets(getValidIntInput(scanner, "Enter total tickets:"));

        // Validate integer input for ticket release rate
        config.setTicketReleaseRate(getValidIntInput(scanner, "Enter ticket release rate:"));

        // Validate integer input for customer retrieval rate
        config.setCustomerRetrievalRate(getValidIntInput(scanner, "Enter customer retrieval rate:"));

        // Validate integer input for vendor count
        config.setVendorCount(getValidIntInput(scanner, "Enter vendor count:"));

        // Validate integer input for customer count
        config.setCustomerCount(getValidIntInput(scanner, "Enter customer count:"));

        saveConfig();
    }

    // Helper method to get valid integer input from the user
    private static int getValidIntInput(Scanner scanner, String prompt) {
        int value = -1;
        boolean valid = false;
        while (!valid) {
            System.out.println(prompt);
            try {
                value = scanner.nextInt();
                valid = true;  // If input is valid, exit the loop
            } catch (InputMismatchException e) {
                // Handle invalid input (non-integer value)
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();  // Clear the buffer
            }
        }
        return value;
    }

    private static void loadConfig() {
        config = Configuration.loadFromFile("CLI/resources/config.json");
    }

    private static void saveConfig() {
        config.saveToFile("CLI/resources/config.json");
    }
}
