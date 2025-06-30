package com.smart.hotel.repository;

import com.smart.hotel.model.Booking;
import com.smart.hotel.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserEmailAndStatus(String email, String status);



}
