package com.smart.hotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "service_items")
public class OtherService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String category;

    // Constructors
    public OtherService() {}

    public OtherService(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
