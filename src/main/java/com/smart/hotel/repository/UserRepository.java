package com.smart.hotel.repository;

import com.smart.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);  // use email for login
}
