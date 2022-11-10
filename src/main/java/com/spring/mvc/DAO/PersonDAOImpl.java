package com.spring.mvc.DAO;

import com.spring.mvc.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAOImpl implements PersonDAO {


    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Person findById(final Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Person p  WHERE email =:personEmail", Person.class).
                setParameter("personEmail", email).getResultList().stream().findAny();

    }

    @Override
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Override
    @Transactional
    public void update(Integer id, Person person) {
        Session session = sessionFactory.getCurrentSession();

        Person personToBeUpdated = session.get(Person.class, id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
        personToBeUpdated.setAddress(person.getAddress());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }

    /////////////////////////////////
    /////// Тест производительности пакетной вставки
    /////////////////////////////////


    @Override
    public void testMultipleUpdate() {
//        List<Person> people = create1000people();
//        long startTime = System.currentTimeMillis();
//        for (Person person : people) {
//            jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES(?, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail(), person.getAddress());
//        }
//
//        System.out.println(System.currentTimeMillis() - startTime);
    }


    @Override
    public void testBatchUpdate() {
//        List<Person> people = create1000people();
//        long startTime = System.currentTimeMillis();
//        jdbcTemplate.batchUpdate("INSERT INTO person(name, age, email, address) VALUES(?, ?, ?, ?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                preparedStatement.setString(1, people.get(i).getName());
//                preparedStatement.setInt(2, people.get(i).getAge());
//                preparedStatement.setString(3, people.get(i).getEmail());
//                preparedStatement.setString(4, people.get(i).getAddress());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return people.size();
//            }
//        });
//
//        System.out.println(System.currentTimeMillis() - startTime);
    }


//    private List<Person> create1000people() {
//        List<Person> people = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            people.add(new Person(i, "Name " + i, 30, "test" + i + "@mail.ru", "some address"));
//        }
//        return people;
//    }
}
