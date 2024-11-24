package com.StudentManagmentSystem.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.StudentManagmentSystem.Entities.Student;
import com.StudentManagmentSystem.Helper.Message;
import com.StudentManagmentSystem.Helper.MessageType;
import com.StudentManagmentSystem.Services.StudentService;
import com.StudentManagmentSystem.forms.StudentForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private StudentService studentService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home() {
        System.out.println("Loading Home Page");

        return "home";
    }

    @RequestMapping("/AddStudent")
    public String AddStudent(Model model) {
        System.out.println("Loading Add Student Page");
        model.addAttribute("studentForm", new StudentForm());
        return "/AddStudent";
    }

    @RequestMapping(value = "/add-student", method = RequestMethod.POST)
    public String addStudent(@Valid @ModelAttribute StudentForm studentForm, BindingResult bindingResult,HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "/AddStudent";
        }
      
        logger.info("Student Form Data : " + studentForm);
        // Saving Student to DB
        // Saving Student data Studentform To Student
        Student student=new Student();

        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        student.setFatherName(studentForm.getFatherName());
        student.setMotherName(studentForm.getMotherName());
        student.setPhoneNumber(studentForm.getPhoneNumber());
        student.setAddress(studentForm.getAddress());

        Student savedStudent=studentService.saveStudent(student);
        logger.info("Saved Student :"+savedStudent);

        Message message = Message.builder().content("Student Added Successfully").type(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/AddStudent";
    }

}
