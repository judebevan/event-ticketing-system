package org.iit.eventsystem.service.impl;

import org.iit.eventsystem.domain.TicketPool;
import org.iit.eventsystem.domain.Vendor;
import org.iit.eventsystem.exception.UserAlreadyExistsException;
import org.iit.eventsystem.exception.UserNotFoundException;
import org.iit.eventsystem.repository.VendorRepository;
import org.iit.eventsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    private boolean isAdminLoggedIn = false;
    private final ReentrantLock lock = new ReentrantLock();
    private int totalTickets;
    private TicketPool ticketPool;

    @Override
    public Vendor createVendor(String username, String email, String password, Long mobileNo, boolean isAdmin) {
        // Check if a vendor with the same email exists
        if (vendorRepository.findVendorByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("Vendor with email " + email + " already exists.");
        }

        Vendor vendor = new Vendor();
        vendor.setUsername(username);
        vendor.setEmail(email);
        vendor.setPassword(password);
        vendor.setMobileNo(mobileNo);
        vendor.setIsAdmin(isAdmin);
        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor vendorLogin(String username, String password) {
        String adminPassword = "admin123";
        if (username.equals("admin") && password.equals(adminPassword)) {
            isAdminLoggedIn = true;
            return vendorRepository.findVendorByUsernameAndPassword(username, password)
                    .orElseThrow(() -> new UserNotFoundException("Invalid admin username or password!"));
        } else {
            return vendorRepository.findVendorByUsernameAndPassword(username, password)
                    .orElseThrow(() -> new UserNotFoundException("Invalid username or password!"));
        }
    }

    @Override
    public synchronized void setConfigurations(int totalTickets, int maxCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        lock.lock();
        try {
            if (isAdminLoggedIn) {
                // Initialize TicketPool
                this.totalTickets = totalTickets;
                ticketPool = new TicketPool(maxCapacity);
                System.out.println("Parameters set successfully!");
            } else {
                throw new SecurityException("Only admin can set parameters.");
            }
        } finally {
            lock.unlock();
        }
    }

}
