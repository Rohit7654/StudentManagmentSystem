package com.StudentManagmentSystem.Services.Implementation;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.StudentManagmentSystem.Entities.Student;
import com.StudentManagmentSystem.Repository.StudentRepository;
import com.StudentManagmentSystem.Services.StudentService;

import lombok.var;

@Service
public class StudentServiceImplement implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {

        String studentID = UUID.randomUUID().toString();
        student.setStudentID(studentID);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        var studentOld= studentRepository.findById(student.getStudentID()).orElse(null);
        studentOld.setName(student.getName());
        studentOld.setEmail(student.getEmail());
        studentOld.setFatherName(student.getFatherName());
        studentOld.setMotherName(student.getMotherName());
        studentOld.setAddress(student.getAddress());

        return studentRepository.save(studentOld);
    }

    @Override
    public void deleteStudent(String Id) {
        var s=studentRepository.findById(Id).orElse(null);
        studentRepository.delete(s);
    }

    @Override
    public boolean isStudentExistByEmail(String email) {
        return studentRepository.existsById(email);

    }

    @Override
    public boolean isStudentExistByPhoneNumber(String phoneNumber) {
        return studentRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Page<Student> getStudentByPhoneNumber(String phoneNumber, int size, int page, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(size, page, sort);
        return studentRepository.findByPhoneNumberContaining(phoneNumber, pageable);
    }

    @Override
    public Page<Student> getStudentByEmail(String email, int size, int page, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(size, page, sort);
        return studentRepository.findByEmailContaining(email, pageable);
    }

    @Override
    public Page<Student> getStudentByName(String name, int size, int page, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(size, page, sort);
        return studentRepository.findByNameContaining(name, pageable);
    }

    @Override
    public Page<Student> getAllStudent(int size, int page, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(size, page, sort);

        return studentRepository.findAll(pageable);
    }

    @Override
    public Student getByID(String id) {
      return studentRepository.findById(id).orElse(null);
    }

}
