package com.smart.hotel.model;

public class OrderItem {

    private Long foodId;
    private int quantity;

    // Getters and Setters
    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
