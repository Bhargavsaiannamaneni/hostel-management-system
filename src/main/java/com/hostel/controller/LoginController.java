package com.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.hostel.entity.Login;
import com.hostel.repository.LoginRepository;

@Controller
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        Login user =
            loginRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            return "redirect:/";
        }

        model.addAttribute("error", "Invalid Username or Password");

        return "login";
    }

    @GetMapping("/create-admin")
    @ResponseBody
    public String createAdmin() {

        if (!loginRepository.existsById("admin")) {

            Login admin = new Login();

            admin.setUsername("admin");
            admin.setPassword("admin123");

            loginRepository.save(admin);
        }

        return "Admin Created Successfully";
    }
    @GetMapping("/logout")
    public String logout() {

        return "redirect:/login";
    }
}