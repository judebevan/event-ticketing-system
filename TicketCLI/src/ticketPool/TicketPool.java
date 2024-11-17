package ticketPool;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Integer> tickets;
    private final int maxCapacity;
    private final int totalTickets; // Total tickets allowed in the system

    public TicketPool(int maxCapacity, int totalTickets) {
        this.tickets = new LinkedList<>();
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }

    public synchronized boolean addTicket(int ticketId) {
        if (tickets.size() >= maxCapacity) {
            return false; // Pool is full
        }
        tickets.add(ticketId);
        notifyAll(); // Notify consumers that a ticket is available
        return true;
    }

    public synchronized Integer removeTicket() {
        while (tickets.isEmpty()) {
            try {
                wait(); // Wait for tickets to be available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return tickets.poll();
    }

    public synchronized int getCurrentSize() {
        return tickets.size();
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }
}
