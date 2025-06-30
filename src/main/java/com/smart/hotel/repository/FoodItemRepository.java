package com.smart.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.hotel.model.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {}
