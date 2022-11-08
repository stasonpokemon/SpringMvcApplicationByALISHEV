package com.spring.mvc.DAO;

import com.spring.mvc.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAOImpl implements PersonDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person findById(final Integer id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny().orElse(null);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM person WHERE email = ?", new Object[]{email}, new BeanPropertyRowMapper<>(Person.class)).
                stream().findAny();
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, age, email, address)  VALUES(?, ?, ?, ?)",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    @Override
    public void update(Integer id, Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ?, address = ?WHERE id = ?",
                person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    /////////////////////////////////
    /////// Тест производительности пакетной вставки
    /////////////////////////////////


    @Override
    public void testMultipleUpdate() {
        List<Person> people = create1000people();
        long startTime = System.currentTimeMillis();
        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES(?, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail(), person.getAddress());
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }


    @Override
    public void testBatchUpdate() {
        List<Person> people = create1000people();
        long startTime = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person(name, age, email, address) VALUES(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, people.get(i).getName());
                preparedStatement.setInt(2, people.get(i).getAge());
                preparedStatement.setString(3, people.get(i).getEmail());
                preparedStatement.setString(4, people.get(i).getAddress());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });

        System.out.println(System.currentTimeMillis() - startTime);
    }


    private List<Person> create1000people() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name " + i, 30, "test" + i + "@mail.ru", "some address"));
        }
        return people;
    }
}
