package com.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hostel.entity.Student;
import com.hostel.repository.StudentRepository;

@Controller
public class StudentFormController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students/add")
    public String addStudentPage(Model model) {

        model.addAttribute("student", new Student());

        return "student-form";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(Student student) {

        Student existingStudent =
                studentRepository.findByEmail(student.getEmail());

        if (existingStudent != null) {

            return "redirect:/students/add";
        }

        studentRepository.save(student);

        return "redirect:/students/view";
    }
}