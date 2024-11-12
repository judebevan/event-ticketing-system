package ticketing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<String> tickets;
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.tickets = Collections.synchronizedList(new ArrayList<>());
        this.maxCapacity = maxCapacity;
    }

    // Producer: Vendor adds tickets to the pool
    public synchronized void addTickets(int numTickets) {
        if (tickets.size() + numTickets <= maxCapacity) {
            for (int i = 0; i < numTickets; i++) {
                tickets.add("Ticket-" + (tickets.size() + 1));
            }
            Logger.log("Added " + numTickets + " tickets to the pool. Current size: " + tickets.size());
        }
    }

    // Consumer: Customer removes a ticket from the pool
    public synchronized boolean removeTicket() {
        if (!tickets.isEmpty()) {
            tickets.remove(0);
            Logger.log("Ticket purchased. Tickets remaining: " + tickets.size());
            return true;
        } else {
            Logger.log("No tickets available for purchase.");
            return false;
        }
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }
}
