package org.iit.eventsystem.repository;

import org.iit.eventsystem.domain.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Purpose:
// - Abstracts database operations for the TicketPool entity, enabling create, read, update, and delete operations.
// - Simplifies database interactions by using inherited methods from JpaRepository.
//
// OOP Concepts Used:
// 1. Abstraction:
//    - Provides high-level methods for interacting with the TicketPool entity without exposing the complexities
//      of the underlying database operations.
// 2. Encapsulation:
//    - Encapsulates all TicketPool-specific database operations within this repository, ensuring separation of
//      concerns and isolating data access logic.
// 3. Inheritance:
//    - Inherits CRUD functionality and advanced features like pagination and sorting from JpaRepository,
//      minimizing boilerplate code and enhancing maintainability.

@Repository
public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {

}

