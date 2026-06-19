package com.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hostel.entity.Fee;

public interface FeeRepository extends JpaRepository<Fee, Long> {

}