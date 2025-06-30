package com.smart.hotel.repository;

import com.smart.hotel.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
   // Optional<Customer> findByEmail(String email);  // For login or verification
}
