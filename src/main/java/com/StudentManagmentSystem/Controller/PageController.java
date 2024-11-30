package com.StudentManagmentSystem.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.StudentManagmentSystem.Entities.Student;
import com.StudentManagmentSystem.Helper.AppConstant;
import com.StudentManagmentSystem.Helper.Message;
import com.StudentManagmentSystem.Helper.MessageType;
import com.StudentManagmentSystem.Services.StudentService;
import com.StudentManagmentSystem.forms.StudentForm;
import com.StudentManagmentSystem.forms.StudentSearchForm;

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
    public String addStudent(@Valid @ModelAttribute StudentForm studentForm, BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "/AddStudent";
        }

        logger.info("Student Form Data : " + studentForm);
        // Saving Student to DB
        // Saving Student data Studentform To Student
        Student student = new Student();

        student.setName(studentForm.getName());
        student.setEmail(studentForm.getEmail());
        student.setFatherName(studentForm.getFatherName());
        student.setMotherName(studentForm.getMotherName());
        student.setPhoneNumber(studentForm.getPhoneNumber());
        student.setAddress(studentForm.getAddress());

        Student savedStudent = studentService.saveStudent(student);
        logger.info("Saved Student :" + savedStudent);

        Message message = Message.builder().content("Student Added Successfully").type(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/AddStudent";
    }

    @RequestMapping("/StudentList")
    public String getAllStudent(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model) {
        Page<Student> pageStudent = studentService.getAllStudent(page, size, sortBy, direction);
        model.addAttribute("PageStudent", pageStudent);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("StudentSearchForm", new StudentSearchForm());
        return "/StudentList";
    }

    @RequestMapping("/search")
    public String SearchStudent(@ModelAttribute StudentSearchForm studentSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model) {

        logger.info("Search Data " + studentSearchForm.getSearchField() + studentSearchForm.getSearchValue());

        Page<Student> pageStudent = null;
        String value = studentSearchForm.getSearchValue();
        if (studentSearchForm.getSearchField().equalsIgnoreCase("name")) {
            pageStudent = studentService.getStudentByName(value, page, size, sortBy, direction);
        } else if (studentSearchForm.getSearchField().equalsIgnoreCase("email")) {
            pageStudent = studentService.getStudentByEmail(value, page, size, sortBy, direction);
        } else if (studentSearchForm.getSearchField().equalsIgnoreCase("phone")) {
            pageStudent = studentService.getStudentByPhoneNumber(value, page, size, sortBy, direction);

        }

        model.addAttribute("PageStudent", pageStudent);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("StudentSearchForm", studentSearchForm);
        return "/studentSearch";
    }

    @RequestMapping("/updatePage")
    public String updateStudent(@ModelAttribute StudentSearchForm studentSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model) {

        Page<Student> pageStudent = studentService.getAllStudent(page, size, sortBy, direction);
        model.addAttribute("PageStudent", pageStudent);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("StudentSearchForm", studentSearchForm);
        return "/updateStudent";
    }

    // update Student View
    @GetMapping("/update-view/{studentID}")
    public String updateStudent(@PathVariable("studentID") String sID, Model model) {
        var s = studentService.getByID(sID);
        StudentForm studentForm = new StudentForm();
        studentForm.setName(s.getName());
        studentForm.setEmail(s.getEmail());
        studentForm.setFatherName(s.getFatherName());
        studentForm.setMotherName(s.getMotherName());
        studentForm.setPhoneNumber(s.getPhoneNumber());
        studentForm.setAddress(s.getAddress());
        model.addAttribute("studentID", sID);
        model.addAttribute("studentForm", studentForm);

        return "/update_view";
    }

    @RequestMapping(value = "/updateStudent/{studentID}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("studentID") String studentID,
            @Valid @ModelAttribute StudentForm studentForm, BindingResult bindingResult, HttpSession session,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "/update_view";
        }

        var s = studentService.getByID(studentID);
        s.setName(studentForm.getName());
        s.setEmail(studentForm.getEmail());
        s.setFatherName(studentForm.getFatherName());
        s.setMotherName(studentForm.getMotherName());
        s.setPhoneNumber(studentForm.getPhoneNumber());
        s.setAddress(studentForm.getAddress());

        var updateStudent = studentService.updateStudent(s);
        logger.info("Update Student : " + updateStudent);

        Message message = Message.builder().content("Student Updated !").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/update-view/" + studentID;
    }

    @RequestMapping("/deletePage")
    public String deleteStudent(@ModelAttribute StudentSearchForm studentSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model) {

        Page<Student> pageStudent = studentService.getAllStudent(page, size, sortBy, direction);
        model.addAttribute("PageStudent", pageStudent);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);
        model.addAttribute("StudentSearchForm", studentSearchForm);
        return "/deleteStudent";
    }
    // detete contact
    @RequestMapping("/delete/{studentId}")
    public String delete(
            @PathVariable("studentId") String studentId,
            HttpSession session) {
        studentService.deleteStudent(studentId);        
        logger.info("contactId {} deleted", studentId);

        session.setAttribute("message",
                Message.builder()
                        .content("Student is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()

        );

        return "redirect:/deletePage";
    }

}
