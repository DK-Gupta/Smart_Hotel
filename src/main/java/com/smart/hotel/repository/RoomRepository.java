package com.smart.hotel.repository;

import com.smart.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByAvailableTrue();
    List<Room> findByQuantityGreaterThan(int quantity);

}