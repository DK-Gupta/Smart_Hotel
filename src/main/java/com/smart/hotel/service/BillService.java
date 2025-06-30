package com.smart.hotel.service;

import com.smart.hotel.model.Bill;
import com.smart.hotel.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public Bill generateBill(String email, double roomCost, double foodCost) {
        Bill bill = new Bill(email, LocalDate.now(), roomCost, foodCost);
        return billRepository.save(bill);
    }

    public List<Bill> getBillsByEmail(String email) {
        return billRepository.findByEmail(email);
    }
    
}
