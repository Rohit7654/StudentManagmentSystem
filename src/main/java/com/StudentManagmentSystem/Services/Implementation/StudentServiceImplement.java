package com.StudentManagmentSystem.Services.Implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StudentManagmentSystem.Entities.Student;
import com.StudentManagmentSystem.Repository.StudentRepository;
import com.StudentManagmentSystem.Services.StudentService;

@Service
public class StudentServiceImplement implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {

        String studentID=UUID.randomUUID().toString();
        student.setStudentID(studentID);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStudent'");
    }

    @Override
    public void deleteStudent(String Id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteStudent'");
    }

    @Override
    public Student getStudentById(String Id) {
        return studentRepository.findById(Id).orElse(null);
    }

    @Override
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> allstudent=studentRepository.findAll();
        return allstudent;
    }

    @Override
    public boolean isStudentExistByid(String id) {
        Student student= studentRepository.findById(id).orElse(null);
        return student!=null? true:false;
    }

    @Override
    public boolean isStudentExistByEmail(String email) {
        Student student= studentRepository.findByEmail(email).orElse(null);
        return student!=null? true:false;
    }

}
