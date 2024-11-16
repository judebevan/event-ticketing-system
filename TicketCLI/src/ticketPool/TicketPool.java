package ticketPool;

import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private int ticketsAvailable;
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketPool(int initialTickets, int maxCapacity) {
        this.ticketsAvailable = initialTickets;
        this.maxCapacity = maxCapacity;
    }

    public void addTickets(int count) {
        lock.lock();
        try {
            if (ticketsAvailable + count <= maxCapacity) {
                ticketsAvailable += count;
                System.out.println(count + " tickets added. Total: " + ticketsAvailable);
            } else {
                System.out.println("Cannot add tickets. Max capacity reached.");
            }
        } finally {
            lock.unlock();
        }
    }

    public void removeTickets(int count) {
        lock.lock();
        try {
            if (ticketsAvailable >= count) {
                ticketsAvailable -= count;
                System.out.println(count + " tickets sold. Remaining: " + ticketsAvailable);
            } else {
                System.out.println("Not enough tickets to sell.");
            }
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

