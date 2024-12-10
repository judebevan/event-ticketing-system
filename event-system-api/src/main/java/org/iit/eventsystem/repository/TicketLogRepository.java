package org.iit.eventsystem.repository;

import org.iit.eventsystem.domain.TicketLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Purpose:
// - Abstracts database operations for the TicketLog entity, allowing the application to perform
//   create, read, update, and delete operations efficiently.
// - Acts as a bridge between the application and the database for TicketLog data.
//
// OOP Concepts Used:
// 1. Abstraction:
//    - Abstracts the complexity of database interactions by exposing high-level methods like `save`, `findById`, and `delete`.
//    - Can work with these methods without dealing with SQL or database internals.
// 2. Encapsulation:
//    - Encapsulates all TicketLog-specific database operations, ensuring separation of concerns
//      and isolating the data access logic within this repository.
// 3. Inheritance:
//    - Inherits functionality from JpaRepository, such as pagination, sorting, and standard CRUD operations,
//      reducing the need to write repetitive code.

@Repository
public interface TicketLogRepository extends JpaRepository<TicketLog, Long> {
}
