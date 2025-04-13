package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE " +
            "(LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) AND c.firstName IS NOT NULL AND c.firstName <> '') " +
            "OR (LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%')) AND c.lastName IS NOT NULL AND c.lastName <> '') " +
            "OR (LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%')) AND c.email IS NOT NULL AND c.email <> '')")
    List<Customer> searchByNameAndEmail(@Param("name") String name, @Param("email") String email);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndIdNot(String email, Long id);
}