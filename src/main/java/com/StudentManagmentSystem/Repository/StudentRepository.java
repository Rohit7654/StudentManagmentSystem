package com.StudentManagmentSystem.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.StudentManagmentSystem.Entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,String>{

    // Custom Query For Database
    boolean existsByEmail(String emailId);
    boolean existsByPhoneNumber(String phoneNumber);

    Page<Student> findByNameContaining(String name,Pageable pageable);

    Page<Student> findByEmailContaining(String email,Pageable pageable);
    Page<Student> findByPhoneNumberContaining(String phoneNumber,Pageable pageable);

}
