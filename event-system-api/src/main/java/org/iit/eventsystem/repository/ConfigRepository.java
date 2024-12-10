package org.iit.eventsystem.repository;

import org.iit.eventsystem.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This repository interface provides database interaction capabilities for the Config entity.
// It uses Spring Data JPA to simplify data access operations and supports both built-in CRUD methods
// and custom queries.
//
// Purpose:
// - Abstracts database interaction for the Config entity, focusing on high-level operations rather than SQL.
// - Allows for custom queries like fetching Config by ID and checking for the existence of records with specific values.
//
// OOP Concepts Used:
// 1. Abstraction: Hides the complexity of database operations, allowing developers to use high-level methods.
// 2. Encapsulation: Ensures database queries are contained within this layer, promoting separation of concerns.
// 3. Inheritance: Inherits methods from JpaRepository, providing built-in CRUD functionality without extra code.
// 4. Declarative Programming: Uses @Query to define custom queries declaratively.

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    @Query("SELECT c FROM Config c WHERE c.id = :id")
    Optional<Config> getConfigById(@Param("id") Long id);

    boolean existsByTotalTicketsAndMaxTicketCapacityAndTicketReleaseRateAndCustomerRetrievalRate(
            Long totalTickets, Long maxTicketCapacity, Long ticketReleaseRate, Long customerRetrievalRate);
}
