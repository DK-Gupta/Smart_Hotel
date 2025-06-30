package com.smart.hotel.controller;

import com.lowagie.text.DocumentException;
import com.smart.hotel.model.Bill;
import com.smart.hotel.model.User;
import com.smart.hotel.repository.UserRepository;
import com.smart.hotel.service.BookingService;
import com.smart.hotel.service.FoodService;
import com.smart.hotel.service.OtherServiceService;
import com.smart.hotel.util.BillPdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class BillingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private OtherServiceService otherServiceService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/bill")
    public String showBill(@AuthenticationPrincipal UserDetails user, Model model) {
        String email = user.getUsername();

        double roomCost = bookingService.calculateTotalRoomCost(email);
        double foodCost = foodService.calculateTotalFoodCost(email);
        double serviceCost = otherServiceService.calculateTotalOtherServiceCost(email);
        double total = roomCost + foodCost + serviceCost;

        model.addAttribute("roomCost", roomCost);
        model.addAttribute("foodCost", foodCost);
        model.addAttribute("serviceCost", serviceCost);
        model.addAttribute("total", total);

        return "billing";
    }

    @GetMapping("/bill/pdf")
    public void downloadPdf(@AuthenticationPrincipal UserDetails user, HttpServletResponse response)
            throws IOException, DocumentException {
        String email = user.getUsername();
        User dbUser = userRepository.findByEmail(email);
        String name = dbUser.getName();

        double roomCost = bookingService.calculateTotalRoomCost(email);
        double foodCost = foodService.calculateTotalFoodCost(email);
        double serviceCost = otherServiceService.calculateTotalOtherServiceCost(email);
        double total = roomCost + foodCost + serviceCost;

        Bill bill = new Bill(email, LocalDate.now(), roomCost, foodCost);
        bill.setTotalAmount(total);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=smart-hotel-bill.pdf");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        new BillPdfExporter(bill, name).export(response);
    }
}
