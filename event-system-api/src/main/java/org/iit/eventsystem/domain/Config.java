package org.iit.eventsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="config")
@Data
public class Config implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "totalTickets", nullable = false)
    private Long totalTickets;

    @Column(name = "maxTicketCapacity", nullable = false)
    private Long maxTicketCapacity;

    @Column(name = "ticketReleaseRate", nullable = false)
    private Long ticketReleaseRate;

    @Column(name = "customerRetrievalRate", nullable = false)
    private Long customerRetrievalRate;



}

