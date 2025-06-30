package com.smart.hotel.service;

import com.smart.hotel.model.FoodItem;
import com.smart.hotel.model.FoodOrder;
import com.smart.hotel.model.OrderItem;
import com.smart.hotel.repository.FoodItemRepository;
import com.smart.hotel.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodItemRepository foodItemRepo;

    @Autowired
    private FoodOrderRepository foodOrderRepo;

    public List<FoodItem> getAllItems() {
        return foodItemRepo.findAll();
    }

    public void placeOrder(String customerEmail, Long foodId, int quantity) {
        FoodItem item = foodItemRepo.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid food ID: " + foodId));

        FoodOrder order = new FoodOrder();
        order.setCustomerEmail(customerEmail);
        order.setFoodItem(item);
        order.setQuantity(quantity);
        order.setOrderTime(LocalDateTime.now());

        double total = item.getPrice() * quantity;
        order.setTotal(total);

        foodOrderRepo.save(order);
    }

    public List<FoodOrder> getOrdersByEmail(String email) {
        return foodOrderRepo.findByCustomerEmail(email);
    }

    public double calculateTotalFoodCost(String email) {
        List<FoodOrder> orders = foodOrderRepo.findByCustomerEmail(email);
        double total = 0;
        for (FoodOrder order : orders) {
            total += order.getTotal();
        }
        return total;
    }
}
