package com.smart.hotel.controller;

import com.smart.hotel.model.Booking;
import com.smart.hotel.model.Customer;
import com.smart.hotel.model.Room;
import com.smart.hotel.model.User;
import com.smart.hotel.repository.BookingRepository;
import com.smart.hotel.repository.RoomRepository;
import com.smart.hotel.repository.UserRepository;
import com.smart.hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingService bookingService;

    // Show form to book room
    @GetMapping("/book-room")
    public String showBookingForm(Model model) {
        List<Room> availableRooms = roomRepository.findByQuantityGreaterThan(0);
        model.addAttribute("rooms", availableRooms);
        return "book-room";
    }

    // Save booking to database
    @PostMapping("/book-room")
    public String bookRoom(@RequestParam Long roomId,
                           @RequestParam String guestName,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
                           @AuthenticationPrincipal UserDetails userDetails,
                           RedirectAttributes redirectAttributes) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));
        
        if (room.getQuantity() <= 0) {
            redirectAttributes.addFlashAttribute("error", "Selected room is not available.");
            return "redirect:/book-room";
        }

        // Decrease room quantity
        room.setQuantity(room.getQuantity() - 1);
        if (room.getQuantity() == 0) {
            room.setAvailable(false); // Optional: set unavailable when quantity = 0
        }
        roomRepository.save(room);

        // Get user and create guest
        User user = userRepository.findByEmail(userDetails.getUsername());
        Customer guest = new Customer();
        guest.setName(guestName);

        // Create booking
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setGuest(guest);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setStatus("Booked");

        bookingRepository.save(booking);
        redirectAttributes.addFlashAttribute("success", "Room booked successfully!");
        return "redirect:/home";
    }

    // View all bookings for logged-in user
    @GetMapping("/bookings")
    public String allBookings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String email = userDetails.getUsername();
        List<Booking> bookings = bookingRepository.findByUserEmailAndStatus(email, "Booked");
        model.addAttribute("bookings", bookings);
        return "all-bookings";
    }

    // Cancel booking and restore room quantity
    @PostMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBookingById(id);
        return "redirect:/bookings";
    }
}
