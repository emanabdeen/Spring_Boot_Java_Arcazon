package com.conestoga.arcazon.repository;

import com.conestoga.arcazon.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}