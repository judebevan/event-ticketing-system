package ticketing;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;

    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        while (Main.isRunning.get()) {
            ticketPool.addTickets(releaseRate);
            try {
                Thread.sleep(5000); // Add tickets every 5 seconds
            } catch (InterruptedException e) {
                Logger.log("Vendor interrupted");
            }
        }
    }
}
