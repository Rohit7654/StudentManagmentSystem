package com.StudentManagmentSystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StudentManagmentSystem.Entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,String>{

    // Custom Query For Database
    Optional<Student> findByEmail(String emailId);
    Optional<Student> findByPhoneNumber(String phoneNumber);

}
