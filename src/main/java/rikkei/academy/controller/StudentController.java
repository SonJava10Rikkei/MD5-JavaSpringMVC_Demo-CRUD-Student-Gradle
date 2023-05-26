package rikkei.academy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.model.Student;
import rikkei.academy.service.IStudentService;
import rikkei.academy.service.StudentServiceIMPL;

import java.util.List;

@Controller
@RequestMapping(value = {"/", "/student"})
public class StudentController {

    @Autowired
    IStudentService studentService;

    //    private IStudentService studentService = new StudentServiceIMPL();
    @GetMapping
    public ModelAndView showListStudent() {
        ModelAndView modelAndView = new ModelAndView("student/list");
        List<Student> studentList = studentService.findAll();
        modelAndView.addObject("listStudent", studentList);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detailStudent(@PathVariable Long id) {
        Student student = studentService.findById(id);
        ModelAndView modelAndView = new ModelAndView("student/detail");
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id) {
        Student student = studentService.findById(id);
        ModelAndView modelAndView = new ModelAndView("student/edit");
        modelAndView.addObject("student", student);
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String actionEdit(@PathVariable Long id, @RequestParam("name") String name,
                             @RequestParam("email") String email, @RequestParam("address") String address) {
        Student student = studentService.findById(id);
        student.setName(name);
        student.setEmail(email);
        student.setAddress(address);
        studentService.save(student);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String actionDetele(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("student/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public String actionCreate(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("address") String address) {
        Long id = 0L;
        if (studentService.findAll().size() == 0) {
            id = 1L;
        } else {
            id = studentService.findAll().get(studentService.findAll().size() - 1).getId() + 1;
        }
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setEmail(email);
        student.setAddress(address);
        studentService.save(student);
        return "redirect:/";
    }
}
