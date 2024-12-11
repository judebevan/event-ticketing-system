package org.iit.eventsystem.controller;

import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.dto.ConfigDto;
import org.iit.eventsystem.service.ConfigService;
import org.iit.eventsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//This class is responsible for handling HTTP requests related to configuration settings.
// OOP Concepts Used:
// 1. Abstraction: The ConfigController class abstracts the details of setting and retrieving configuration settings, providing high-level methods for interacting with the configuration service.
// 2. Encapsulation: Encapsulates the logic related to configuration management within the controller layer, ensuring separation of concerns.
// 3. Dependency Injection: Uses the @Autowired annotation to inject instances of ConfigService and VendorService, promoting loose coupling and flexibility.

@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @Autowired
    private VendorService vendorService;

    @PostMapping("/init-config")
    public ResponseEntity<String> setConfigurations(@RequestBody ConfigDto configDto) {
        try {
            // Validate ConfigDto values
            validateConfigDto(configDto);

            // Extract fields and pass to VendorService
            int totalTickets = configDto.getTotalTickets();
            int maxCapacity = configDto.getMaxTicketCapacity();
            int ticketReleaseRate = configDto.getTicketReleaseRate();
            int customerRetrievalRate = configDto.getCustomerRetrievalRate();

            vendorService.setConfigurations(totalTickets, maxCapacity, ticketReleaseRate, customerRetrievalRate);
            return ResponseEntity.ok("Parameters set successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

//    {
//        "totalTickets": 500,
//        "maxCapacity": 100,
//        "ticketReleaseRate": 10,
//        "customerRetrievalRate": 5
//    }

    @GetMapping("/get-config")
    public ResponseEntity<Config> getConfig() {
        Config config = configService.getCurrentConfig();
        if (config == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(config);
    }

    @PostMapping("/set-config")
    public ResponseEntity<Config> resetConfig(@RequestBody ConfigDto configDto) {
        // Validate ConfigDto values manually
        validateConfigDto(configDto);

        // Proceed with the rest of the logic
        Config config = configService.resetConfigValue(configDto);
        return ResponseEntity.ok(config);
    }

    private void validateConfigDto(ConfigDto configDto) {
        // Validate individual fields
        if (configDto.getTotalTickets() <= 0) {
            throw new IllegalArgumentException("Total tickets must be greater than 0.");
        }
        if (configDto.getMaxTicketCapacity() <= 0) {
            throw new IllegalArgumentException("Max capacity must be greater than 0.");
        }
        if (configDto.getTicketReleaseRate() <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be greater than 0.");
        }
        if (configDto.getCustomerRetrievalRate() <= 0) {
            throw new IllegalArgumentException("Customer retrieval rate must be greater than 0.");
        }

        // Additional validation: maxCapacity cannot exceed totalTickets
        if (configDto.getMaxTicketCapacity() > configDto.getTotalTickets()) {
            throw new IllegalArgumentException("Max capacity cannot exceed total tickets.");
        }

        // Additional validation: ticketReleaseRate cannot exceed maxCapacity
        if (configDto.getTicketReleaseRate() > configDto.getMaxTicketCapacity()) {
            throw new IllegalArgumentException("Ticket release rate cannot exceed max capacity.");
        }

        // Additional validation: ticketReleaseRate cannot exceed totalTickets
        if (configDto.getTicketReleaseRate() > configDto.getTotalTickets()) {
            throw new IllegalArgumentException("Ticket release rate cannot exceed total tickets.");
        }
    }

}
