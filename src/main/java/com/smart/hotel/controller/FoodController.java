package com.smart.hotel.controller;

import com.smart.hotel.model.FoodItem;
import com.smart.hotel.model.FoodOrder;
import com.smart.hotel.model.OrderItem;
import com.smart.hotel.model.OtherService;
import com.smart.hotel.model.OtherServiceOrder;
import com.smart.hotel.service.FoodService;
import com.smart.hotel.service.OtherServiceService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private OtherServiceService  otherServiceService;
    // Show menu from DB
    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<FoodItem> items = foodService.getAllItems();
        model.addAttribute("items", items);
        return "food-menu";
    }

    // Show static food order form
    @GetMapping("/order")
    public String showOrderPage(Model model) {
        List<FoodItem> dbItems = foodService.getAllItems(); // 🔁 from DB instead of hardcoded

        OrderItemListWrapper wrapper = new OrderItemListWrapper();
        wrapper.setOrderItems(dbItems.stream().map(f -> {
            OrderItem item = new OrderItem();
            item.setFoodId(f.getId());
            item.setQuantity(0);
            return item;
        }).toList());

        model.addAttribute("foodItems", dbItems);
        model.addAttribute("orderItemListWrapper", wrapper);
        return "order-food";
    }


    // Place order with customer email and multiple items
    @PostMapping("/place")
    public String placeOrder(@ModelAttribute OrderItemListWrapper orderItemListWrapper,
                             @AuthenticationPrincipal User user,
                             RedirectAttributes redirectAttributes) {
        String email = user.getUsername();

        for (OrderItem item : orderItemListWrapper.getOrderItems()) {
            if (item != null) {
                Long foodId = item.getFoodId();
                Integer quantity = item.getQuantity();

                if (foodId != null && quantity != null && quantity > 0) {
                    try {
                        foodService.placeOrder(email, foodId, quantity);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Skipping invalid item: " + e.getMessage());
                    }
                }
            }
        }

        // ✅ Add success message
        redirectAttributes.addFlashAttribute("success", "✅ Food order placed successfully!");
        return "redirect:/food/order";
    }



    // Show all previous orders for the logged-in user
    @GetMapping("/my-orders")
    public String viewMyOrders(Model model, HttpSession session, Principal principal) {
        String email = principal.getName();

        List<FoodOrder> foodOrders = foodService.getOrdersByEmail(email);
        model.addAttribute("orders", foodOrders);

        // ✅ Fetch OtherServiceOrders from DB
        List<OtherServiceOrder> serviceOrders = otherServiceService.getServiceOrdersByEmail(email);
        model.addAttribute("selectedServices", serviceOrders);  // note: this now holds order entities

        return "my-orders";
    }



    // Wraps the list of order items for form submission
    public static class OrderItemListWrapper {
        private List<OrderItem> orderItems;

        public List<OrderItem> getOrderItems() {
            return orderItems;
        }

        public void setOrderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
        }
    }

    // Helper method to create dummy food items manually
    private FoodItem createItem(Long id, String name, double price, String category) {
        FoodItem item = new FoodItem();
        item.setName(name);
        item.setPrice(price);
        item.setCategory(category);
        try {
            java.lang.reflect.Field field = FoodItem.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(item, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
}
