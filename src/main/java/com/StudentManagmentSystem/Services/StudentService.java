package com.StudentManagmentSystem.Services;

import org.springframework.data.domain.Page;

import com.StudentManagmentSystem.Entities.Student;

public interface StudentService {

    Student saveStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudent(String Id);

    Student getByID(String id);

    Page<Student> getStudentByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order);
    Page<Student> getStudentByEmail(String email, int size, int page, String sortBy, String order);
    Page<Student> getStudentByName(String name, int size, int page, String sortBy, String order);
    Page<Student> getAllStudent(int size, int page, String sortBy, String order);

    boolean isStudentExistByPhoneNumber(String phoneNumber);
    boolean isStudentExistByEmail(String email);
}
