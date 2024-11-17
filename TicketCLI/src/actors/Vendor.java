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
            boolean added = pool.addTickets(releaseRate);
            if (!added) {
                Logger.log("Vendor could not add tickets. Waiting for customer purchases.");
            }
            try {
                Thread.sleep(1000); // Simulate release rate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
