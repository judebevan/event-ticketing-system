package org.iit.eventsystem.service;

import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.dto.ConfigDto;

public interface ConfigService {

    Config resetConfigValue(ConfigDto configDto);

    Config getCurrentConfig();
}
