package actors;

import logger.Logger;
import ticketPool.TicketPool;

public class Vendor implements Runnable {
    private final TicketPool pool;
    private final int releaseRate;

    public Vendor(TicketPool pool, int releaseRate) {
        this.pool = pool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            pool.addTickets(releaseRate);
            Logger.log("Vendor added " + releaseRate + " tickets. Tickets available: " + pool.getTicketsAvailable());
            try {
                Thread.sleep(1000); // Simulate release rate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
