package com.hostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.hostel.entity.Allocation;
import com.hostel.entity.Room;
import com.hostel.entity.Student;
import com.hostel.repository.AllocationRepository;
import com.hostel.repository.RoomRepository;
import com.hostel.repository.StudentRepository;

@Controller
public class AllocationController {

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/allocations")
    public String allocationPage(Model model) {

        model.addAttribute("students",
                studentRepository.findAll());

        model.addAttribute("rooms",
                roomRepository.findAll());

        return "allocations";
    }

    @PostMapping("/allocate")
    public String saveAllocation(
            @ModelAttribute Allocation allocation,
            Model model) {

        Student student = allocation.getStudent();

        List<Allocation> existingAllocations =
                allocationRepository.findByStudent(student);

        if (!existingAllocations.isEmpty()) {

            model.addAttribute("error",
                    "Student is already allocated!");

            model.addAttribute("students",
                    studentRepository.findAll());

            model.addAttribute("rooms",
                    roomRepository.findAll());

            return "allocations";
        }

        Room room = roomRepository
                .findById(allocation.getRoom().getId())
                .orElse(null);

        if (room == null) {

            model.addAttribute("error",
                    "Room not found!");

            model.addAttribute("students",
                    studentRepository.findAll());

            model.addAttribute("rooms",
                    roomRepository.findAll());

            return "allocations";
        }

        Integer occupiedBeds = room.getOccupiedBeds();

        if (occupiedBeds == null) {
            occupiedBeds = 0;
        }

        if (occupiedBeds >= room.getCapacity()) {

            model.addAttribute("error",
                    "Room is Full!");

            model.addAttribute("students",
                    studentRepository.findAll());

            model.addAttribute("rooms",
                    roomRepository.findAll());

            return "allocations";
        }

        room.setOccupiedBeds(occupiedBeds + 1);

        roomRepository.save(room);

        allocationRepository.save(allocation);

        return "redirect:/allocations/view";
    }

    @GetMapping("/allocations/view")
    public String viewAllocations(Model model) {

        model.addAttribute(
                "allocations",
                allocationRepository.findAll());

        return "view-allocations";
    }

    @GetMapping("/allocations/delete/{id}")
    public String deleteAllocation(@PathVariable Long id) {

        Allocation allocation =
                allocationRepository.findById(id).orElse(null);

        if (allocation != null) {

            Room room = allocation.getRoom();

            if (room != null &&
                    room.getOccupiedBeds() != null &&
                    room.getOccupiedBeds() > 0) {

                room.setOccupiedBeds(
                        room.getOccupiedBeds() - 1);

                roomRepository.save(room);
            }

            allocationRepository.deleteById(id);
        }

        return "redirect:/allocations/view";
    }
}