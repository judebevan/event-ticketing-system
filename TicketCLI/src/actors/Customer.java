package actors;

import logger.Logger;
import ticketPool.TicketPool;

public class Customer implements Runnable {
    private final TicketPool pool;
    private final int retrievalRate;

    public Customer(TicketPool pool, int retrievalRate) {
        this.pool = pool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            pool.removeTickets(retrievalRate);
            Logger.log("Customer retrieved " + retrievalRate + " tickets. Tickets remaining: " + pool.getTicketsAvailable());
            try {
                Thread.sleep(1000); // Simulate retrieval rate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
