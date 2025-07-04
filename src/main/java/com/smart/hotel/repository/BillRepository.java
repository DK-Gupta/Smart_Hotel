package com.smart.hotel.repository;

import com.smart.hotel.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByEmail(String email);
}
