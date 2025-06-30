package com.smart.hotel.controller;

import com.smart.hotel.model.OtherService;
import com.smart.hotel.service.OtherServiceService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class OtherServiceController {

    @Autowired
    private OtherServiceService serviceService;

    @GetMapping("/services")
    public String showServicesPage(Model model) {
        List<OtherService> services = serviceService.getAllServices();
        model.addAttribute("services", services);
        return "other-services";
    }

    @PostMapping("/services/select")
    public String handleServiceSelection(@RequestParam("selectedServices") List<Long> selectedServiceIds,
                                         Principal principal,
                                         RedirectAttributes redirectAttributes) {
        String email = principal.getName();
        serviceService.saveSelectedServices(email, selectedServiceIds);

        redirectAttributes.addFlashAttribute("serviceSuccess", "Services added successfully!");
        return "redirect:/food/my-orders";
    }


}

