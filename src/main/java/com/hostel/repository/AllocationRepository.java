package com.hostel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hostel.entity.Allocation;
import com.hostel.entity.Student;

public interface AllocationRepository
        extends JpaRepository<Allocation, Long> {

    List<Allocation> findByStudent(Student student);

}