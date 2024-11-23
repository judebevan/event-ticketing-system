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

    @Column(name = "total_tickets", nullable = false)
    private Long totalTickets;

    @Column(name = "max_ticket_capacity", nullable = false)
    private Long maxTicketCapacity;

    @Column(name = "ticket_release_rate", nullable = false)
    private Long ticketReleaseRate;

    @Column(name = "customer_retrieval_rate", nullable = false)
    private Long customerRetrievalRate;

}

