package com.smart.hotel.repository;

import com.smart.hotel.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByCustomerEmail(String email);
}
