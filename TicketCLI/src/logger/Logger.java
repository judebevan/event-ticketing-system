package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Logger class to log messages to the console and a log file in the resources directory
public class Logger {
    private static final String LOG_FILE = "TicketCLI/resources/ticketing_log.txt";

    // Log a message to the console and a log file in the resources directory with a timestamp prefix
    // The method is synchronized to prevent multiple threads from writing to the log file simultaneously
    public static synchronized void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = timestamp + " - " + message;
        System.out.println(logMessage); // Log to console

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Failed to log message: " + e.getMessage());
        }
    }
}
