package ticketing;

import java.io.*;
import java.util.Scanner;

public class Configuration implements Serializable {
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int vendorCount;
    private int customerCount;

    // Getters and Setters for each field
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }

    public int getVendorCount() { return vendorCount; }
    public void setVendorCount(int vendorCount) { this.vendorCount = vendorCount; }

    public int getCustomerCount() { return customerCount; }
    public void setCustomerCount(int customerCount) { this.customerCount = customerCount; }

    // Load configuration from JSON file using manual parsing
    public static Configuration loadFromFile(String filename) {
        Configuration config = new Configuration();
        try (Scanner scanner = new Scanner(new File(filename))) {
            String content = scanner.useDelimiter("\\A").next(); // Read entire file

            // Parsing manually: Assuming the JSON structure is predictable and simple
            config.setMaxTicketCapacity(Integer.parseInt(extractValue(content, "maxTicketCapacity")));
            config.setTotalTickets(Integer.parseInt(extractValue(content, "totalTickets")));
            config.setTicketReleaseRate(Integer.parseInt(extractValue(content, "ticketReleaseRate")));
            config.setCustomerRetrievalRate(Integer.parseInt(extractValue(content, "customerRetrievalRate")));
            config.setVendorCount(Integer.parseInt(extractValue(content, "vendorCount")));
            config.setCustomerCount(Integer.parseInt(extractValue(content, "customerCount")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    // Helper method to extract values from JSON string
    private static String extractValue(String content, String key) {
        String searchKey = "\"" + key + "\":";
        int start = content.indexOf(searchKey) + searchKey.length();
        int end = content.indexOf(",", start);
        if (end == -1) {
            end = content.indexOf("}", start);
        }
        return content.substring(start, end).trim().replaceAll("\"", ""); // Clean up and return the value
    }

    // Save configuration to JSON file using manual construction
    public void saveToFile(String filename) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n");
        jsonBuilder.append("\"maxTicketCapacity\": ").append(maxTicketCapacity).append(",\n");
        jsonBuilder.append("\"totalTickets\": ").append(totalTickets).append(",\n");
        jsonBuilder.append("\"ticketReleaseRate\": ").append(ticketReleaseRate).append(",\n");
        jsonBuilder.append("\"customerRetrievalRate\": ").append(customerRetrievalRate).append(",\n");
        jsonBuilder.append("\"vendorCount\": ").append(vendorCount).append(",\n");
        jsonBuilder.append("\"customerCount\": ").append(customerCount).append("\n");
        jsonBuilder.append("}");

        try (Writer writer = new FileWriter(filename)) {
            writer.write(jsonBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "maxTicketCapacity=" + maxTicketCapacity +
                ", totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", vendorCount=" + vendorCount +
                ", customerCount=" + customerCount +
                '}';
    }
}
