package org.iit.eventsystem.service;

import org.iit.eventsystem.domain.Vendor;

public interface VendorService {

    Vendor createVendor(String username, String email, String password, Long mobileNo, boolean isAdmin);

    Vendor vendorLogin(String username, String password);

    void setConfigurations(int totalTickets, int maxCapacity, int ticketReleaseRate, int customerRetrievalRate);
}
