package com.example.demo.model;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="employee")
public class Employee {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String gmailid;
    private Long mobileno;
    private String designation;
    private String gender;
    private String course;
    private String fileName;
    private String filePath;
    
    @Transient
    private MultipartFile file;
    private String fileType;
    private String fileSize;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Timestamp createdDate;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGmailid() {
        return gmailid;
    }
    public void setGmailid(String gmailid) {
        this.gmailid = gmailid;
    }
    public Long getMobileno() {
        return mobileno;
    }
    public void setMobileno(Long mobileno) {
        this.mobileno = mobileno;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", gmailid=" + gmailid + ", mobileno=" + mobileno
                + ", designation=" + designation + ", gender=" + gender + ", course=" + course + ", fileName="
                + fileName + ", filePath=" + filePath + ", file=" + file + ", fileType=" + fileType + ", fileSize="
                + fileSize + ", createdDate=" + createdDate + "]";
    }
	
}
