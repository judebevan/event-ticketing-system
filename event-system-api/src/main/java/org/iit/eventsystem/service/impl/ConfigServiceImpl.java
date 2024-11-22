package org.iit.eventsystem.service.impl;


import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.dto.ConfigDto;
import org.iit.eventsystem.repository.ConfigRepository;
import org.iit.eventsystem.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public Config getCurrentConfig() {
        return configRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Configuration not found."));
    }

    @Override
    public Config resetConfigValue(ConfigDto configDto) {
        Config config = getCurrentConfig();
        config.setTotalTickets((long) configDto.getTotalTickets());
        config.setMaxTicketCapacity((long) configDto.getMaxCapacity());
        config.setTicketReleaseRate((long) configDto.getTicketReleaseRate());
        config.setCustomerRetrievalRate((long) configDto.getCustomerRetrievalRate());
        return configRepository.save(config);
    }
}
