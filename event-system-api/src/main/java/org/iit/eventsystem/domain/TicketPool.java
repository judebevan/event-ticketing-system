package org.iit.eventsystem.domain;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {
    private final ConcurrentLinkedQueue<String> tickets = new ConcurrentLinkedQueue<>();
    private final int maxCapacity;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean addTickets(String ticket) {
        lock.lock();
        try {
            if (tickets.size() < maxCapacity) {
                tickets.add(ticket);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public String removeTicket() {
        lock.lock();
        try {
            return tickets.poll();
        } finally {
            lock.unlock();
        }
    }

    public int getAvailableTickets() {
        return tickets.size();
    }
}
