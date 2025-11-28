package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAO {

    private static final Logger log = LogManager.getLogger(UserDAO.class);
    private final Session session = HibernateUtil.getSessionFactory().openSession();

    public Long save (User user) {
        Transaction transaction = session.beginTransaction();

        Long id = (Long) session.save(user);
        transaction.commit();

        log.info("User saved with ID {}", id);

        return id;
    }

    public Optional<User> findById(Long id) {
        User user = session.get(User.class, id);

        if (user != null) {
            log.info("User found by ID: {}", user.toString());
        } else {
            log.info("User with ID {} not found", id);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
        query.setParameter("email", email);

        User user = query.uniqueResult();

        if (user != null) {
            log.info("User found by email: {}", user.toString());
        } else {
            log.info("User with email {} not found", email);
        }

        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        Query<User> query = session.createQuery("FROM User*", User.class);
        List<User> users = query.list();

        log.info("Found {} users.", users.size());
        return users;
    }

    public void update(User user) {
        Transaction transaction = session.beginTransaction();

        session.update(user);
        transaction.commit();

        log.info("User with ID {} updated!", user.getId());
    }

    public void delete(Long id) {
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);

        if (user != null) {
            session.delete(user);
            log.info("User with ID {} successfully deleted!", id);
        } else {
            log.info("User with ID {} not found for deleting!", id);
        }

        transaction.commit();
    }
}
