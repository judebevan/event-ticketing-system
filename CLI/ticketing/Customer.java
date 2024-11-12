package ticketing;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalRate;

    public Customer(TicketPool ticketPool, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (Main.isRunning.get()) {
            for (int i = 0; i < retrievalRate; i++) {
                boolean success = ticketPool.removeTicket();
                if (!success) {
                    Logger.log("Attempted to buy ticket but none were available.");
                }
            }
            try {
                Thread.sleep(5000); // Attempt to buy tickets every 5 seconds
            } catch (InterruptedException e) {
                Logger.log("Customer interrupted");
            }
        }
    }
}
