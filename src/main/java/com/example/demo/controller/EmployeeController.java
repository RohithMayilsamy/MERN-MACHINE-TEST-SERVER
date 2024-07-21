package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Employee;

import com.example.demo.repository.EmployeeRepository;



@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeeController {
    private static Logger log = LoggerFactory.getLogger(EmployeeController.class);
    public static String uploadDirectory = "D:\\react\\reactapp\\machine\\src\\EmployeeImage";

    @Autowired
    private EmployeeRepository eRepo;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return eRepo.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return eRepo.findById(id).get();
    }
    
    @PostMapping("/employees")
    public @ResponseBody ResponseEntity<?> createEmployee(
            @RequestParam("name") String name,
            @RequestParam("gmailid") String gmailid,
            @RequestParam("mobileno") Long mobileno,
            @RequestParam("designation") String designation,
            @RequestParam("gender") String gender,
            @RequestParam("course") String course,
            @RequestParam("file") MultipartFile file) {
        try {
            // Create directory if not exists
            File dir = new File(uploadDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Extract file details
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            String fileType = file.getContentType();
            long size = file.getSize();
            String fileSize = String.valueOf(size);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            // Log details
            log.info("Name: " + name);
            log.info("FileName: " + file.getOriginalFilename());
            log.info("FileType: " + file.getContentType());
            log.info("FileSize: " + file.getSize());

            // Save the file locally
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(file.getBytes());
            stream.close();

            // Set employee details
            Employee employee = new Employee();
            employee.setName(name);
            employee.setGmailid(gmailid);
            employee.setMobileno(mobileno);
            employee.setDesignation(designation);
            employee.setGender(gender);
            employee.setCourse(course);
            employee.setFileName(fileName);
            employee.setFilePath(filePath);
            employee.setFileType(fileType);
            employee.setFileSize(fileSize);
            employee.setCreatedDate(currentTimestamp);

            // Save employee to repository
            eRepo.save(employee);

            log.info("Employee Created");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Employee Saved With Image - ", fileName);
            return new ResponseEntity<>("Employee Saved With File - " + fileName, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("gmailid") String gmailid,
            @RequestParam("mobileno") Long mobileno,
            @RequestParam("designation") String designation,
            @RequestParam("gender") String gender,
            @RequestParam("course") String course,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        return eRepo.findById(id).map(employee -> {
            try {
                // Update employee details
                employee.setName(name);
                employee.setGmailid(gmailid);
                employee.setMobileno(mobileno);
                employee.setDesignation(designation);
                employee.setGender(gender);
                employee.setCourse(course);
                // Check if a new file is provided
                if (file != null) {
                    // Delete the old file if it exists
                    File oldFile = new File(employee.getFilePath());
                    if (oldFile.exists() && !oldFile.isDirectory()) {
                        oldFile.delete();
                    }

                    // Save the new file
                    String fileName = file.getOriginalFilename();
                    String filePath = Paths.get(uploadDirectory, fileName).toString();
                    String fileType = file.getContentType();
                    long size = file.getSize();
                    String fileSize = String.valueOf(size);
                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                    // Log details
                    log.info("Updated FileName: " + file.getOriginalFilename());
                    log.info("Updated FileType: " + file.getContentType());
                    log.info("Updated FileSize: " + file.getSize());

                    // Save the file locally
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    stream.write(file.getBytes());
                    stream.close();

                    // Update file details in employee
                    employee.setFileName(fileName);
                    employee.setFilePath(filePath);
                    employee.setFileType(fileType);
                    employee.setFileSize(fileSize);
                    employee.setCreatedDate(currentTimestamp);
                }

                // Save the updated employee
                eRepo.save(employee);
                log.info("Employee Updated");

                return new ResponseEntity<>(employee, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Exception: " + e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        return eRepo.findById(id).map(employee -> {
            File file = new File(employee.getFilePath());
            if (file.exists()) {
                if (file.delete()) {
                    log.info("File deleted successfully");
                } else {
                    log.error("Failed to delete file");
                }
            }
            eRepo.delete(employee);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }
}
