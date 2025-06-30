package com.smart.hotel.service;

import com.smart.hotel.model.Booking;
import com.smart.hotel.model.Room;
import com.smart.hotel.repository.BookingRepository;
import com.smart.hotel.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void saveBooking(Booking booking) {
        // Set default status when booking
        booking.setStatus("Booked");

        // Mark the room as unavailable
        Room room = booking.getRoom();
        room.setAvailable(false);
        roomRepository.save(room);

        bookingRepository.save(booking);
    }


    public void cancelBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        Room room = booking.getRoom();

        // Increase quantity
        room.setQuantity(room.getQuantity() + 1);
        room.setAvailable(true);
        roomRepository.save(room);

        booking.setStatus("Cancelled");
        bookingRepository.save(booking);
    }
    
 // BookingService.java (Partial update)
    public double calculateTotalRoomCost(String email) {
        List<Booking> bookings = bookingRepository.findByUserEmailAndStatus(email, "Booked");
        return bookings.stream()
                .mapToDouble(b -> b.getRoom().getPrice())
                .sum();
    }
    

}
