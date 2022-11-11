package com.spring.mvc.controller;

import com.spring.mvc.entity.Person;
import com.spring.mvc.service.PersonService;
import com.spring.mvc.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonValidator personValidator;

    @GetMapping()
    public String showPeoplePage(Model model) {
        List<Person> people = personService.findAll();
        model.addAttribute("people", people);
        return "people/show-people";
    }


    @GetMapping("/{id}")
    public String showPersonPage(@PathVariable("id") Long id,
                                 Model model) {
        Person person = personService.findById(id);
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
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPersonPage(@PathVariable("id") Long id,
                                 Model model) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit";
    }

    // Исправить валидацию email
    @PatchMapping("/{id}")
    public String saveEditPerson(@ModelAttribute("person") @Valid Person person,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Long id) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.update(id, person);

        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personService.delete(id);
        return "redirect:/people";
    }

}
