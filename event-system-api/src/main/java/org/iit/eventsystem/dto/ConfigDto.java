package org.iit.eventsystem.dto;

import lombok.Data;

//@Data
public class ConfigDto {
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketMaxCapacity() {
        return maxTicketCapacity;
    }

    public void setTicketMaxCapacity(int maxCapacity) {
        this.maxTicketCapacity = maxCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // 1. Encapsulation: Protects the internal state by using private fields and controlled access through getters/setters.
    // 2. Abstraction: Provides a unified object for transferring configuration data, hiding individual field details.
}
