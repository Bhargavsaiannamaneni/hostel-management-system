package com.hostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.hostel.entity.Room;
import com.hostel.repository.RoomRepository;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    // Open Room Registration Page
    @GetMapping("/rooms")
    public String roomsPage() {
        return "rooms";
    }

    // Save Room
    @PostMapping("/rooms/save")
    public String saveRoom(Room room) {

        room.setOccupiedBeds(0);

        roomRepository.save(room);

        return "redirect:/rooms";
    }

    // View All Rooms
    @GetMapping("/rooms/view")
    public String viewRooms(Model model) {

        List<Room> rooms = roomRepository.findAll();

        model.addAttribute("rooms", rooms);

        return "rooms-list";
    }

    // Edit Room
    @GetMapping("/rooms/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model) {

        Room room = roomRepository.findById(id).orElse(null);

        model.addAttribute("room", room);

        return "edit-room";
    }

    // Update Room
    @PostMapping("/rooms/update")
    public String updateRoom(@ModelAttribute Room room) {

        roomRepository.save(room);

        return "redirect:/rooms/view";
    }

    // Delete Room
    @GetMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {

        roomRepository.deleteById(id);

        return "redirect:/rooms/view";
    }
}