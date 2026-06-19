package com.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hostel.entity.Complaint;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

}