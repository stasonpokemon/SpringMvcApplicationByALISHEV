package com.spring.mvc.controller;

import com.spring.mvc.DAO.PersonDAO;
import com.spring.mvc.entity.Person;

import javax.validation.Valid;

import com.spring.mvc.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private PersonValidator personValidator;

    @GetMapping()
    public String showPeoplePage(Model model) {
        List<Person> people = personDAO.findAll();
        model.addAttribute("people", people);
        return "people/show-people";
    }


    @GetMapping("/{id}")
    public String showPersonPage(@PathVariable("id") Integer id,
                                 Model model) {
        Person person = personDAO.findById(id);
        model.addAttribute("person", person);
        return "people/show-person";
    }

    @GetMapping("/new")
    public String newPersonPage(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String saveNewPerson(@ModelAttribute("person") @Valid Person person,
                                BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(@PathVariable("id") Integer id,
                                 Model model) {
        model.addAttribute("person", personDAO.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String saveEditPerson(@ModelAttribute("person") @Valid Person person,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Integer id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personDAO.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Integer id) {
        personDAO.delete(id);
        return "redirect:/people";
    }

}
