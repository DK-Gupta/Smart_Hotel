package com.smart.hotel.service;

import com.smart.hotel.model.FoodOrder;
import com.smart.hotel.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private FoodOrderRepository orderRepository;

    public List<FoodOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public FoodOrder getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public FoodOrder saveOrder(FoodOrder order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
