package com.StudentManagmentSystem.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class Student {

    @Id
    private String studentID;  
    
    private String name;
    private String fatherName;    
    private String motherName;    
    private String phoneNumber;    
    private String email;    
    private String address;    
    private String profilePicture;  

}
