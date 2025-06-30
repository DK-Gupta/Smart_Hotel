package com.smart.hotel.controller;

import com.smart.hotel.model.Room;
import com.smart.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Display all rooms
    @GetMapping
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms"; // corresponds to templates/rooms.html
    }

    // Show form to add a room
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-room";
    }

    // Handle room submission
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    // Delete a room
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return "redirect:/rooms";
    }
}
