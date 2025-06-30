package com.smart.hotel.service;

import com.smart.hotel.model.OtherService;
import com.smart.hotel.model.OtherServiceOrder;
import com.smart.hotel.repository.OtherServiceOrderRepository;
import com.smart.hotel.repository.OtherServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OtherServiceService {

    @Autowired
    private OtherServiceRepository serviceRepo;

    public List<OtherService> getAllServices() {
        return serviceRepo.findAll();
    }
    public List<OtherService> getServicesByIds(List<Long> ids) {
        return serviceRepo.findAllById(ids);
    }
    @Autowired
    private OtherServiceOrderRepository orderRepo;

    public void saveSelectedServices(String email, List<Long> serviceIds) {
        List<OtherService> services = serviceRepo.findAllById(serviceIds);

        for (OtherService service : services) {
            OtherServiceOrder order = new OtherServiceOrder(email, service, LocalDateTime.now());
            orderRepo.save(order);
        }
    }

    public List<OtherServiceOrder> getServiceOrdersByEmail(String email) {
        return orderRepo.findByCustomerEmail(email);
    }

    public double calculateTotalOtherServiceCost(String email) {
        List<OtherServiceOrder> orders = orderRepo.findByCustomerEmail(email);
        double total = 0;
        for (OtherServiceOrder o : orders) {
            total += o.getService().getPrice();
        }
        return total;
    }

}

