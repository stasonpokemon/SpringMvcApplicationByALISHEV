package com.spring.mvc.util;

import com.spring.mvc.DAO.PersonDAO;
import com.spring.mvc.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Autowired
    private PersonDAO personDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        //  Проверяем, есть ли в бд пользователь с такой же почтой
        if (personDAO.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email address is used");
        }

    }
}
