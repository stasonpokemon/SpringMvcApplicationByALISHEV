package com.spring.mvc.controller;

import com.spring.mvc.entity.Person;
import com.spring.mvc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PersonService personService;


    @GetMapping
    public String adminPage(@ModelAttribute( "person") Person person,
                            Model model) {
        model.addAttribute("people", personService.findAll());
        return "admin/adminPage";
    }

    @PatchMapping("/add")
    public String addAdmin(@ModelAttribute("person") Person person) {
        Long personId = person.getId();
        Person personById = personService.findById(personId);
        personById.setName("Admin: " + personById.getName());
        personService.update(personId, personById);
        return "redirect:/people";
    }


}
