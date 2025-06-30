package com.smart.hotel.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity

public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // To link with user|
    

    private LocalDate billDate;
    private double roomCost;
    private double foodCost;
    private double totalAmount;
	public LocalDate getBillDate() {
		return billDate;
	}
	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}
	public double getRoomCost() {
		return roomCost;
	}
	public void setRoomCost(double roomCost) {
		this.roomCost = roomCost;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getFoodCost() {
		return foodCost;
	}
	public void setFoodCost(double foodCost) {
		this.foodCost = foodCost;
	}
	public Bill() {}

    public Bill(String email, LocalDate billDate, double roomCost, double foodCost) {
        this.email = email;
        this.billDate = billDate;
        this.roomCost = roomCost;
        this.foodCost = foodCost;
        this.totalAmount = roomCost + foodCost;
    }
	
}

