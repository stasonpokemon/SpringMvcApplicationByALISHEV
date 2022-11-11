package com.spring.mvc.util;

import com.spring.mvc.entity.Person;
import com.spring.mvc.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //  Проверяем, есть ли в бд пользователь с такой же почтой
        if (personService.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email address is used");
        }

    }
}
