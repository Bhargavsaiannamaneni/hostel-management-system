package com.hostel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hostel.entity.Fee;
import com.hostel.repository.FeeRepository;

@Controller
public class FeeController {

    @Autowired
    private FeeRepository feeRepository;

    @GetMapping("/fees")
    public String feePage(Model model) {

        model.addAttribute("fee", new Fee());

        return "fees";
    }

    @PostMapping("/fees/save")
    public String saveFee(Fee fee) {

        feeRepository.save(fee);

        return "redirect:/fees/view";
    }

    @GetMapping("/fees/view")
    public String viewFees(Model model) {

        List<Fee> fees = feeRepository.findAll();

        model.addAttribute("fees", fees);

        return "view-fees";
    }

    @GetMapping("/fees/edit/{id}")
    public String editFee(@PathVariable Long id, Model model) {

        Fee fee = feeRepository.findById(id).orElse(null);

        model.addAttribute("fee", fee);

        return "edit-fee";
    }

    @PostMapping("/fees/update")
    public String updateFee(Fee fee) {

        feeRepository.save(fee);

        return "redirect:/fees/view";
    }

    @GetMapping("/fees/delete/{id}")
    public String deleteFee(@PathVariable Long id) {

        feeRepository.deleteById(id);

        return "redirect:/fees/view";
    }
}