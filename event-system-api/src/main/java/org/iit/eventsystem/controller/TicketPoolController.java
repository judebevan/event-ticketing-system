package org.iit.eventsystem.controller;

import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.domain.TicketPool;
import org.iit.eventsystem.dto.TicketPoolStatus;
import org.iit.eventsystem.dto.TicketRequestDto;
import org.iit.eventsystem.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This class is responsible for handling HTTP requests related to ticket pool management.
// OOP Concepts Used:
// 1. Abstraction: The TicketPoolController class abstracts the details of adding and purchasing tickets, providing high-level methods for interacting with the configuration service.
// 2. Encapsulation: Encapsulates the logic related to ticket pool management within the controller layer, ensuring separation of concerns.
// 3. Dependency Injection: Uses the @Autowired annotation to inject an instance of ConfigService, promoting loose coupling and flexibility.

@RestController
@RequestMapping("/tickets")
public class TicketPoolController {

    @Autowired
    private ConfigService configService;

    @PostMapping("/add-tickets")
    public ResponseEntity<String> addTickets(@RequestBody TicketRequestDto addRequest) {
        // Validate the type field
        if (!"add".equalsIgnoreCase(addRequest.getType())) {
            return ResponseEntity.badRequest().body("Invalid type. Only 'add' is allowed.");
        }

        try {
            configService.addTicketsToPool(addRequest.getCount(), addRequest.getVendorId());
            return ResponseEntity.ok("Tickets added successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid vendor ID.");
        }
    }

    @PostMapping("/purchase-tickets")
    public ResponseEntity<String> purchaseTickets(@RequestBody TicketRequestDto purchaseRequest) {
        if (!"purchase".equalsIgnoreCase(purchaseRequest.getType())) {
            return ResponseEntity.badRequest().body("Invalid type. Only 'purchase' is allowed.");
        }
        try {
            configService.purchaseTicketsFromPool(purchaseRequest.getCount(), purchaseRequest.getCustomerId());
            return ResponseEntity.ok("Tickets purchased successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid customer ID.");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<TicketPoolStatus> getTicketPoolStatus() {
        Config currentConfig = configService.getCurrentConfig();
        TicketPool ticketPool = configService.getTicketPool();

        if (ticketPool == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Return an empty body on error
        }

        TicketPoolStatus status = new TicketPoolStatus();
        status.setAvailableTickets(ticketPool.getAvailableTickets());
        status.setReleasedTickets(ticketPool.getReleasedTickets());
        status.setTotalTickets(currentConfig.getTotalTickets());
        status.setMaxTicketCapacity(currentConfig.getMaxTicketCapacity());

        return ResponseEntity.ok(status);  // Spring will convert this to JSON
    }
}
