package com.smart.hotel.service;

import com.smart.hotel.model.Room;
import com.smart.hotel.repository.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableTrue();
    }

    // ✅ Preload sample room data on app start
    @PostConstruct
    public void initSampleRooms() {
        if (roomRepository.count() == 0) {
            Room r1 = new Room();
            r1.setRoomNumber("101");
            r1.setType("Deluxe");
            r1.setPrice(1500.0);
            r1.setAvailable(true);

            Room r2 = new Room();
            r2.setRoomNumber("102");
            r2.setType("Suite");
            r2.setPrice(2500.0);
            r2.setAvailable(true);

            Room r3 = new Room();
            r3.setRoomNumber("103");
            r3.setType("Economy");
            r3.setPrice(900.0);
            r3.setAvailable(false);

            roomRepository.saveAll(List.of(r1, r2, r3));
        }
    }
}
