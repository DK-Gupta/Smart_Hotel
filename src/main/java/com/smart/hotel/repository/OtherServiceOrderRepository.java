package com.smart.hotel.repository;

import com.smart.hotel.model.OtherServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OtherServiceOrderRepository extends JpaRepository<OtherServiceOrder, Long> {
    List<OtherServiceOrder> findByCustomerEmail(String email);
}
