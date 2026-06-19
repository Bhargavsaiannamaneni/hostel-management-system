package com.hostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hostel.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Search student by name
    List<Student> findByNameContaining(String name);

    // Check duplicate email
    Student findByEmail(String email);

}