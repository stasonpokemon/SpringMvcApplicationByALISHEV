package com.spring.mvc.DAO;

import com.spring.mvc.entity.Person;

import java.util.List;

public interface PersonDAO {

    /**
     * @return
     */
    List<Person> findAll();

    /**
     * @param id
     * @return
     */
    Person findById(Integer id);

    /**
     * @param person
     */
    void save(Person person);

    /**
     * @param id
     * @param person
     */
    void update(Integer id, Person person);

    void delete(Integer id);
}
