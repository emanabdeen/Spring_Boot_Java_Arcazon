package com.conestoga.arcazon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "phone", length = 25)
    private String phone;

    @Column(name = "address_line1", length = 120)
    private String addressLine1;

    @Column(name = "city", length = 80)
    private String city;

    @Column(name = "province", length = 40)
    private String province;

    @Column(name = "postal_code", length = 15)
    private String postalCode;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private Set<com.conestoga.arcazon.model.Order> orders = new LinkedHashSet<>();


    public String getCustomerName() {
        return firstName + " " + lastName;
    }

}