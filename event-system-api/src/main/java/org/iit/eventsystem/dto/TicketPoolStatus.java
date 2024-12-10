package org.iit.eventsystem.dto;

public class TicketPoolStatus {
    private long availableTickets;
    private long releasedTickets;
    private long totalTickets;
    private long maxTicketCapacity;

    public long getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(long availableTickets) {
        this.availableTickets = availableTickets;
    }

    public long getReleasedTickets() {
        return releasedTickets;
    }

    public void setReleasedTickets(long releasedTickets) {
        this.releasedTickets = releasedTickets;
    }

    public long getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(long totalTickets) {
        this.totalTickets = totalTickets;
    }

    public long getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(long maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    // 1. Encapsulation: Protects the internal state by using private fields and controlled access through getters/setters.
    // 2. Abstraction: Provides a unified object for transferring configuration data, hiding individual field details.
}
