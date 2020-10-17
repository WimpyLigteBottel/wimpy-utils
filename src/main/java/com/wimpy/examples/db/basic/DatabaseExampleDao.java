package com.wimpy.examples.db.basic;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DatabaseExampleDao {

    @PersistenceContext
    private EntityManager entityManager;


    public Optional<User> findLastUser() {


        List<User> userList = entityManager.createQuery("from User u ORDER BY u.id DESC", User.class).setMaxResults(1).getResultList();

        if (userList.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(userList.get(0));

    }

    public Optional<User> find(String username) {


        try {
            User user = entityManager.createQuery("select u from User u where u.username = :username desc", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            return Optional.ofNullable(user);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public void batchInsert(List<User> user) {
        Session session = entityManager.unwrap(Session.class);

        session.doWork((Connection connection) -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO USER (username,password,inserted,updated) VALUES (?,?,?,?)")) {

                for (User u : user) {
                    ps.setString(1, u.getUsername());
                    ps.setString(2, u.getPassword());
                    ps.setTime(3, new Time(System.currentTimeMillis()));
                    ps.setTime(4, new Time(System.currentTimeMillis()));
                    ps.addBatch();
                }


                ps.executeBatch();
            }
        });

    }

    public void massPasswordUpdate() {
        List<User> users = entityManager.createQuery("select u from User u", User.class)
                .getResultList();


        List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());

        int count = entityManager.createQuery("Update User u "
                + " SET u.password = 'updated' "
                + " WHERE u.username in (:usernames)")
                .setParameter("usernames", usernames)
                .executeUpdate();

        Logger.info("massPasswordUpdate() modified [count={}]", count);


    }
}
