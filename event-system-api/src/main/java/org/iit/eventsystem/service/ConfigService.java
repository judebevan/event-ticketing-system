package org.iit.eventsystem.service;

import org.iit.eventsystem.domain.Config;
import org.iit.eventsystem.domain.TicketPool;
import org.iit.eventsystem.dto.ConfigDto;

public interface ConfigService {

    Config resetConfigValue(ConfigDto configDto);

    Config getCurrentConfig();

    void addTicketsToPool(long ticketsToAdd);

    void purchaseTicketsFromPool(long ticketsToPurchase);

    TicketPool getTicketPool();
}
