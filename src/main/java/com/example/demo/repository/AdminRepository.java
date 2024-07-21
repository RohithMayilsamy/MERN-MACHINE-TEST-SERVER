package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Admin;



public interface AdminRepository extends JpaRepository<Admin,
Long> {
	 Optional<Admin> findByUsernameAndPwd(String username, String pwd);
}
