package org.iit.eventsystem.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private String username;
    private String email;
    private String password;
    private Long mobileNo;
    private Boolean isAdmin;

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    // 1. Encapsulation: Protects the internal state by using private fields and controlled access through getters/setters.
    // 2. Abstraction: Provides a unified object for transferring configuration data, hiding individual field details.
}

