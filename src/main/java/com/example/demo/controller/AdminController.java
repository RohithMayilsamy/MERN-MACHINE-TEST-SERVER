package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;


@RestController
@RequestMapping("/api/")
@CrossOrigin("*")
public class AdminController {
	@Autowired
    private AdminRepository eRepo;
	 @GetMapping("/admin")
	    public List<Admin> getAllAdmin() {
	        return eRepo.findAll();
	    }

	    @GetMapping("/admin/{id}")
	    public Admin getAdminById(@PathVariable Long id) {
	        return eRepo.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
	    }

	    @PostMapping("/admin")
	    public Admin saveAdmineDetails(@RequestBody Admin admin) {
	        return eRepo.save(admin);
	    }

	    @PutMapping("/admin")
	    public Admin updateAdmin(@RequestBody Admin admin) {
	        return eRepo.save(admin);
	    }

	    @DeleteMapping("/admin/{id}")
	    public ResponseEntity<HttpStatus> deleteAdminById(@PathVariable Long id) {
	        eRepo.deleteById(id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    @PostMapping("/admin/login")
	    public ResponseEntity<Admin> login(@RequestBody Admin admin) {
	        Admin existingAdmin = eRepo.findByUsername(admin.getUsername());
	        if (existingAdmin != null && existingAdmin.getPwd().equals(admin.getPwd())) {
	            return new ResponseEntity<>(existingAdmin, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	    }
}
