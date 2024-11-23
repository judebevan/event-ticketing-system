package org.iit.eventsystem.service.impl;


import lombok.extern.java.Log;
import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.dto.ConfigDto;
import org.iit.eventsystem.repository.ConfigRepository;
import org.iit.eventsystem.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Logger log = Logger.getLogger(ConfigServiceImpl.class.getName());

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public Config getCurrentConfig() {
        log.info("Fetching current configuration...");
        Config config =  configRepository.getConfigById(1L)
                .orElseThrow(() -> new RuntimeException("Configuration not found."));
        log.info("Current configuration retrieved: " + config);
        return config;
    }

    @Override
    public Config resetConfigValue(ConfigDto configDto) {
        log.info("Resetting configuration values...");
        Config config = configRepository.getConfigById(1L).orElse(new Config());
        if (config.getId() == null) {
            config.setId(1L); // Ensure ID is set for new records
            log.info("New configuration created with ID: 1");
        }

        config.setTotalTickets((long) configDto.getTotalTickets());
        config.setMaxTicketCapacity((long) configDto.getMaxCapacity());
        config.setTicketReleaseRate((long) configDto.getTicketReleaseRate());
        config.setCustomerRetrievalRate((long) configDto.getCustomerRetrievalRate());

        Config savedConfig = configRepository.save(config);
        log.info(String.format("Configuration saved with ID: %d, Values: %s", savedConfig.getId(), savedConfig));
        return savedConfig;

    }

}
