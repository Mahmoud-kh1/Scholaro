package com.learnharbor.Scholaro.controller;

import com.learnharbor.Scholaro.model.Person;
import com.learnharbor.Scholaro.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepo;


    @RequestMapping("/dashboard")
    public String displayDashboard(Model model,Authentication authentication, HttpSession session) {

        System.out.println("AUTH OBJECT = " + authentication);
        if (authentication != null) {
            System.out.println("isAuthenticated: " + authentication.isAuthenticated());
            System.out.println("name: '" + authentication.getName() + "'");
            Object principal = authentication.getPrincipal();
            System.out.println("principal class = " + (principal == null ? "null" : principal.getClass().getName()));
            System.out.println("principal = " + principal);
        } else {

            System.out.println("authentication is null");
        }



        Person person = personRepo.readByEmail(authentication.getName());
        session.setAttribute("loggedInPerson", person);
        model.addAttribute("username", person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        return "dashboard.html";
    }







}