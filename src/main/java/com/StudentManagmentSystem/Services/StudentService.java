package com.StudentManagmentSystem.Services;

import java.util.List;

import com.StudentManagmentSystem.Entities.Student;

public interface StudentService {

    Student saveStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudent(String Id);
    Student getStudentById(String Id);
    Student getStudentByEmail(String email);
    List<Student> getAllStudent();
    boolean isStudentExistByid(String id);
    boolean isStudentExistByEmail(String email);
}
