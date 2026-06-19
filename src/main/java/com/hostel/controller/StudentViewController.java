package com.hostel.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import com.hostel.entity.Student;
import com.hostel.repository.StudentRepository;

@Controller
public class StudentViewController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students/view")
    public String viewStudents(Model model) {

        List<Student> students = studentRepository.findAll();

        model.addAttribute("students", students);

        return "students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentRepository.deleteById(id);

        return "redirect:/students/view";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        Student student = studentRepository.findById(id).orElse(null);

        model.addAttribute("student", student);

        return "edit-student";
    }
    @GetMapping("/students/search")
    public String searchStudent(
            @RequestParam String keyword,
            Model model) {

        List<Student> students =
                studentRepository.findByNameContaining(keyword);

        model.addAttribute("students", students);

        return "students";
    }
    @PostMapping("/students/update")
    public String updateStudent(Student student) {

        studentRepository.save(student);

        return "redirect:/students/view";
    }
    
}