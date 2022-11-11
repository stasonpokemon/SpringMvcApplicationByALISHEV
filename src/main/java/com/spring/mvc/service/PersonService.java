package com.spring.mvc.service;

import com.spring.mvc.entity.Person;
import com.spring.mvc.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    @Autowired
    private PersonRepo personRepo;

    public List<Person> findAll() {
        return personRepo.findAll();
    }

    public Person findById(Long id) {
        Optional<Person> optionalPerson = personRepo.findById(id);
        return optionalPerson.orElse(null);
    }

    @Transactional
    public Person save(Person person) {
        person.setCreateAt(new Date());
        return personRepo.save(person);
    }

    @Transactional
    public void update(Long id, Person person) {
        person.setId(id);
        personRepo.save(person);
    }

    @Transactional
    public void delete(Long id) {
        personRepo.deleteById(id);
    }


    public Optional<Person> findByEmail(String email) {
        return personRepo.findByEmail(email);
    }
}
