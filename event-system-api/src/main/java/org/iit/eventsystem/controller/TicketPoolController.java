package org.iit.eventsystem.controller;

import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.domain.TicketPool;
import org.iit.eventsystem.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketPoolController {
    @Autowired
    private ConfigService configService;

    @PostMapping("/add-tickets")
    public ResponseEntity<String> addTickets(@RequestBody long ticketsToAdd) {
        try {
            configService.addTicketsToPool(ticketsToAdd);
            return ResponseEntity.ok("Tickets added successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/purchase-tickets")
    public ResponseEntity<String> purchaseTickets(@RequestBody long ticketsToPurchase) {
        try {
            configService.purchaseTicketsFromPool(ticketsToPurchase);
            return ResponseEntity.ok("Tickets purchased successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getTicketPoolStatus() {
        Config currentConfig = configService.getCurrentConfig();
        TicketPool ticketPool = configService.getTicketPool();

        if (ticketPool == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Ticket pool is not available.");
        }

        return ResponseEntity.ok(String.format(
                "Available Tickets: %d, Released Tickets: %d, Total Tickets: %d, Max Capacity: %d",
                ticketPool.getAvailableTickets(),
                ticketPool.getReleasedTickets(),
                currentConfig.getTotalTickets(),
                currentConfig.getMaxTicketCapacity()
        ));
    }
}
