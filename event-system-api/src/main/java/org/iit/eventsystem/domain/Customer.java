package org.iit.eventsystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name ="customer")
@Data
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "phone", length = 10)
    private Long mobileNo;

    @Column(name = "is_premium", nullable = false)
    private boolean isPremium;

    public void setIs_premium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

}
