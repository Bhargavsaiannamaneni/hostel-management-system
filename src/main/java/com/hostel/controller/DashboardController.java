package com.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hostel.entity.Fee;
import com.hostel.repository.AllocationRepository;
import com.hostel.repository.ComplaintRepository;
import com.hostel.repository.FeeRepository;
import com.hostel.repository.RoomRepository;
import com.hostel.repository.StudentRepository;

@Controller
public class DashboardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("studentCount",
                studentRepository.count());

        model.addAttribute("roomCount",
                roomRepository.count());

        model.addAttribute("allocationCount",
                allocationRepository.count());

        model.addAttribute("availableRooms",
                roomRepository.count() - allocationRepository.count());

        model.addAttribute("complaintCount",
                complaintRepository.count());

        double totalFees = feeRepository.findAll()
                .stream()
                .mapToDouble(Fee::getAmount)
                .sum();

        model.addAttribute("totalFees", totalFees);

        return "dashboard";
    }
}