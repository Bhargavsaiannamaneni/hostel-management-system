package com.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hostel.entity.Login;

public interface LoginRepository extends JpaRepository<Login, String> {

    Login findByUsernameAndPassword(String username, String password);

}