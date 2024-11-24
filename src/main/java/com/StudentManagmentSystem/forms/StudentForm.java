package com.StudentManagmentSystem.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentForm {

    @NotBlank(message = "Name Is Required")
    private String name;    
    @NotBlank(message = "Father Name Is Required")
    private String fatherName;    
    @NotBlank(message = "Mother Name Is Required")
    private String motherName;    
    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    private String phoneNumber;    
    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email; 
    @NotBlank(message = "Address Is Required")
    private String address;    
    private String profilePicture; 
}
