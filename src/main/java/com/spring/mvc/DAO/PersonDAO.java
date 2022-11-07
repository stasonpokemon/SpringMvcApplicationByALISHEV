package com.spring.mvc.DAO;

import com.spring.mvc.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDAO {

    List<Person> findAll();

    Person findById(Integer id);

    Optional<Person> findByEmail(String email);

    void save(Person person);

    void update(Integer id, Person person);

    void delete(Integer id);

    void testMultipleUpdate();

    void testBatchUpdate();
}
