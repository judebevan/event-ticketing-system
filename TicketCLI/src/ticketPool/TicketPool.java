package ticketPool;

import logger.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private int ticketsAvailable;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketPool(int initialTickets, int maxCapacity) {
        if (initialTickets > maxCapacity) {
            throw new IllegalArgumentException("Initial tickets cannot exceed maximum capacity.");
        }

        this.ticketsAvailable = initialTickets;
        this.maxCapacity = maxCapacity;
    }

    public boolean addTickets(int count) {
        lock.lock();
        try {

            if (ticketsAvailable + count <= maxCapacity) {
                ticketsAvailable += count;
                Logger.log(count + " tickets added. Total tickets now: " + ticketsAvailable);
                return true;
            } else {
                Logger.log("Cannot add " + count + " tickets. Max capacity reached. Tickets available: " + ticketsAvailable);
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean removeTickets(int count) {
        lock.lock();
        try {
            if (ticketsAvailable >= count) {
                ticketsAvailable -= count;
                Logger.log(count + " tickets sold. Remaining tickets: " + ticketsAvailable);
                return true;
            } else {
                Logger.log("Not enough tickets available for sale. Tickets available: " + ticketsAvailable);
                return false;
            }
        } catch (Exception e) {
            Logger.log("Error in removing tickets: " + e.getMessage());
            return false;
        } finally {
            lock.unlock();
        }
    }


    public int getTicketsAvailable() {
        lock.lock();
        try {
            return ticketsAvailable;
        } finally {
            lock.unlock();
        }
    }

}

