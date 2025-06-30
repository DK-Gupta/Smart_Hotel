package com.smart.hotel.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
 

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}