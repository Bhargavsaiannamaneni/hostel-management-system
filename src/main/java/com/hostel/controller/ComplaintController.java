package com.hostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hostel.entity.Complaint;
import com.hostel.repository.ComplaintRepository;

@Controller
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/complaints")
    public String complaintPage(Model model) {

        model.addAttribute("complaint", new Complaint());

        return "complaints";
    }

    @PostMapping("/complaints/save")
    public String saveComplaint(Complaint complaint) {

        complaint.setStatus("PENDING");

        complaintRepository.save(complaint);

        return "redirect:/complaints/view";
    }

    @GetMapping("/complaints/view")
    public String viewComplaints(Model model) {

        List<Complaint> complaints =
                complaintRepository.findAll();

        model.addAttribute("complaints", complaints);

        return "view-complaints";
    }

    @GetMapping("/complaints/resolve/{id}")
    public String resolveComplaint(@PathVariable Long id) {

        Complaint complaint =
                complaintRepository.findById(id).orElse(null);

        if (complaint != null) {

            complaint.setStatus("RESOLVED");

            complaintRepository.save(complaint);
        }

        return "redirect:/complaints/view";
    }
}