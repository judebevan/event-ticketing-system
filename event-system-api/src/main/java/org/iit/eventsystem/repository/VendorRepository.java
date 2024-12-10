package org.iit.eventsystem.repository;

import org.iit.eventsystem.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Purpose:
// - Abstracts database operations for the Vendor entity, allowing create, read, update, and delete operations.
// - Provides methods to fetch vendors based on credentials, email, or unique ID, supporting business logic like authentication and vendor lookup.
//
// OOP Concepts Used:
// 1. Abstraction:
//    - Hides the complexities of database interactions and provides simple, high-level methods for accessing Vendor data.
//    - For example, `findVendorByEmail` abstracts the underlying query logic, allowing developers to fetch a vendor by email seamlessly.
// 2. Encapsulation:
//    - Encapsulates all database operations related to Vendor within this repository, promoting separation of concerns.
//    - Ensures that data access logic is isolated from other parts of the application.
// 3. Inheritance:
//    - Inherits built-in CRUD functionality and features like pagination and sorting from JpaRepository, reducing the need for repetitive code.

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {

    Optional<Vendor> findVendorByUsernameAndPassword(String username, String password);

    Optional<Vendor> findVendorByEmail(String email);

    Optional<Vendor> findVendorById(long id);

}
