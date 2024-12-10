package org.iit.eventsystem.repository;

import org.iit.eventsystem.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// Purpose:
// - Abstracts database operations for the Customer entity, reducing the need for boilerplate code.
// - Provides methods to fetch customers based on username/password, email, or ID, supporting login and lookup functionality.
//
// OOP Concepts Used:
// 1. Abstraction:
//    - Hides the complexity of database operations and exposes high-level methods for interacting with customer data.
//    - Can work with methods like `findCustomerByEmail` without needing to write raw SQL queries.
// 2. Encapsulation:
//    - Ensures that all database operations for Customer are encapsulated within this repository layer,
//      promoting separation of concerns and isolating data access logic.
// 3. Inheritance:
//    - Inherits functionality from JpaRepository, such as `save`, `findById`, and `delete`,
//      enabling seamless CRUD operations without additional code.

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerByUsernameAndPassword(String username, String password);

    Optional<Customer> findCustomerByEmail(String email);

    Optional<Customer> findCustomerById(long customerId);

}
