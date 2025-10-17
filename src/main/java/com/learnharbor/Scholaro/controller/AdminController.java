package com.learnharbor.Scholaro.controller;

import com.learnharbor.Scholaro.model.Courses;
import com.learnharbor.Scholaro.model.Person;
import com.learnharbor.Scholaro.model.ScholaroClasses;
import com.learnharbor.Scholaro.repository.ClassRepository;
import com.learnharbor.Scholaro.repository.CoursesRepository;
import com.learnharbor.Scholaro.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ClassRepository classRepository;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;


    @RequestMapping(value = "/displayClasses", method = RequestMethod.GET)
    public ModelAndView displayClasses(Model model, Authentication auth) {

        List<ScholaroClasses> classes = classRepository.findAll();
        System.out.println(auth.getAuthorities());
        ModelAndView mav = new ModelAndView("classes.html");
        mav.addObject("eazyClass", new ScholaroClasses());
        mav.addObject("eazyClasses", classes);
        return mav;
    }


    @RequestMapping(value = "/addNewClass", method = RequestMethod.POST)
    public String addNewClass(Model model, @Valid @ModelAttribute("eazyClass")  ScholaroClasses eazyClass, Errors errors) {
        classRepository.save(eazyClass);
        return "redirect:/admin/displayClasses";
    }


    @RequestMapping(value = "/deleteClass", method = RequestMethod.GET)
    public ModelAndView deleteClass(@RequestParam int id) {
       Optional<ScholaroClasses> optional = classRepository.findById( id);
       for(Person person : optional.get().getPersons()) {
           person.setEazyClass(null);
           personRepository.save(person);
       }
       classRepository.deleteById(id);
       ModelAndView mav = new ModelAndView("redirect:/admin/displayClasses");
       return mav;
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<ScholaroClasses> eazyClass = classRepository.findById(classId);
        modelAndView.addObject("eazyClass",eazyClass.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("eazyClass",eazyClass.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }


    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        ScholaroClasses eazyClass = (ScholaroClasses) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        classRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        ScholaroClasses eazyClass = (ScholaroClasses) session.getAttribute("eazyClass");
        Optional<Person> person = personRepository.findById((long) personId);
        person.get().setEazyClass(null);
        eazyClass.getPersons().remove(person.get());
        ScholaroClasses eazyClassSaved = classRepository.save(eazyClass);
        session.setAttribute("eazyClass",eazyClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }


    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        List<Courses> courses = coursesRepository.findAll();
        modelAndView.addObject("course", new Courses());
        session.setAttribute("courses",courses);
        modelAndView.addObject("courses",courses);
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(@ModelAttribute("course") Courses course, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/displayCourses");
        coursesRepository.save(course);
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(@RequestParam(value = "id") int classId, HttpSession session,
                                     @RequestParam(value = "error", required = false)
                                     String error) {
        Optional<Courses> course = coursesRepository.findById(classId);
        ModelAndView modelAndView = new ModelAndView("course_student.html");
        modelAndView.addObject("courses", course.get());
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses",course.get());
        if(error != null) {
            modelAndView.addObject("errorMessage", "Invalid Id entered!!!");
        }
        return modelAndView;
    }


    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @PostMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId,
                                                HttpSession session) {
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById((long) personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses",courses);
        ModelAndView modelAndView = new
                ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }











}
