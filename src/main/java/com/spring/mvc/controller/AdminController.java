package com.spring.mvc.controller;

import com.spring.mvc.DAO.PersonDAO;
import com.spring.mvc.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonDAO personDAO;


    @GetMapping
    public String adminPage(@ModelAttribute( "person") Person person,
                            Model model) {
        model.addAttribute("people", personDAO.findAll());
        return "admin/adminPage";
    }

    @PatchMapping("/add")
    public String addAdmin(@ModelAttribute("person") Person person) {
        Integer personId = person.getId();
        Person personById = personDAO.findById(personId);
        personById.setName("Admin: " + personById.getName());
        personDAO.update(personId, personById);
        return "redirect:/people";
    }


}
