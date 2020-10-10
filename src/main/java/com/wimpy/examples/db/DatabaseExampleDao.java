package com.wimpy.examples.db;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
public class DatabaseExampleDao {

    @PersistenceContext
    private EntityManager entityManager;


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
}
