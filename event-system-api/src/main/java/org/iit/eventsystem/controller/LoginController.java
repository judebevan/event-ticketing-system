package org.iit.eventsystem.controller;

import org.iit.eventsystem.domain.Customer;
import org.iit.eventsystem.domain.Vendor;
import org.iit.eventsystem.dto.*;
import org.iit.eventsystem.service.CustomerService;
import org.iit.eventsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//This class is responsible for handling HTTP requests related to user login and registration.
// OOP Concepts Used:
// 1. Abstraction: The LoginController class abstracts the details of user login and registration, providing high-level methods for interacting with the vendor and customer services.
// 2. Encapsulation: Encapsulates the logic related to user management within the controller layer, ensuring separation of concerns.
// 3. Dependency Injection: Uses the @Autowired annotation to inject instances of VendorService and CustomerService, promoting loose coupling and flexibility.

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("vendor/register")
    public ResponseEntity<Vendor> registerVendor(@RequestBody VendorDTO vendorDTO) {
        Vendor createdVendor = vendorService.createVendor(
                vendorDTO.getUsername(),
                vendorDTO.getEmail(),
                vendorDTO.getPassword(),
                vendorDTO.getMobileNo(),
                vendorDTO.getIsAdmin()
        );
        return ResponseEntity.ok(createdVendor);
    }

    @PostMapping("vendor/login")
    public ResponseEntity<String> vendorLogin(@RequestBody VendorLoginDto vendorDTO) {
        String username = vendorDTO.getUsername();
        String password = vendorDTO.getPassword();

        if (username == null || password == null) {
            return ResponseEntity.badRequest().build(); // Handle missing fields
        }

        Vendor vendor = vendorService.vendorLogin(username, password);
        return ResponseEntity.ok("Vendor login successful.");
    }

    @PostMapping("customer/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerDto customerDTO) {
        Customer createdCustomer = customerService.createCustomer(
                customerDTO.getUsername(),
                customerDTO.getEmail(),
                customerDTO.getPassword(),
                customerDTO.getMobileNo(),
                customerDTO.getIs_premium()
        );
        return ResponseEntity.ok(createdCustomer);
    }

    @PostMapping("customer/login")
    public ResponseEntity<String> customerLogin(@RequestBody CustomerLoginDto customerLoginDto) {
        String username = customerLoginDto.getUsername();
        String password = customerLoginDto.getPassword();

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Username and password are required.");
        }

        customerService.customerLogin(username, password);
        return ResponseEntity.ok("Customer login successful.");
    }


}
