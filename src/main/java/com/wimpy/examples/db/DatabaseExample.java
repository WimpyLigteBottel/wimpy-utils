package com.wimpy.examples.db;

import com.wimpy.aop.annotations.Timing;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class DatabaseExample {

    private final DatabaseExampleCrudDao databaseExampleCrudDao;
    private final DatabaseExampleDao databaseExampleDao;
    private final PasswordEncoder passwordEncoder;


    public DatabaseExample(DatabaseExampleCrudDao databaseExampleCrudDao, DatabaseExampleDao databaseExampleDao, PasswordEncoder passwordEncoder) {
        this.databaseExampleCrudDao = databaseExampleCrudDao;
        this.databaseExampleDao = databaseExampleDao;
        this.passwordEncoder = passwordEncoder;
    }





    /*
    For creating rather use post method but for each of access i am
    going to use GET call


    Also sending password like this is very unsecure

     */

    @GetMapping("/db/create")
    public ResponseEntity<Object> createUserInTable(@RequestParam String name, @RequestParam String password) throws URISyntaxException {

        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));

        if (!databaseExampleCrudDao.findById(name).isPresent()) {
            databaseExampleCrudDao.save(user);
            Logger.info("user created!");
        }


        URI location = new URI("/db/find");
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/db/creates/v1")
    @Transactional
    @Timing
    public ResponseEntity<Object> createUserInTableV1() {

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 9999; i++) {
            User e = new User();
            e.setUsername(i + "");
            e.setPassword(i + "");//technically i should encode the password but i not doing it to save time
            e.setInserted(new Date());
            e.setUpdated(new Date());
            users.add(e);
        }

        databaseExampleDao.batchInsert(users);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/db/creates/v2")
    @Transactional
    @Timing
    public ResponseEntity<Object> createUserInTableV2() {

        List<User> users = new ArrayList<>();
        for (int i = 10001; i < 20000; i++) {
            User e = new User();
            e.setUsername(i + "");
            e.setPassword(i + "");//technically i should encode the password but i not doing it to save time
            e.setInserted(new Date());
            e.setUpdated(new Date());
            users.add(e);
        }

        databaseExampleCrudDao.saveAll(users);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/db/creates/v3")
    @Transactional
    @Timing
    public ResponseEntity<Object> createUserInTableV3() {

        List<User> users = new ArrayList<>();
        for (int i = 20001; i < 30000; i++) {
            User e = new User();
            e.setUsername(i + "");
            e.setPassword(i + "");//technically i should encode the password but i not doing it to save time
            e.setInserted(new Date());
            e.setUpdated(new Date());
            users.add(e);
        }


        users.forEach(user -> {
            databaseExampleCrudDao.save(user);
        });

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/db/find")
    public ResponseEntity<User> find(@RequestParam String name) {

        Optional<User> user = databaseExampleCrudDao.findById(name);


        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();

    }


    @GetMapping("/db/findAll")
    @Timing
    public ResponseEntity<List<User>> findAll() {

        List<User> users = new ArrayList<>();

        databaseExampleCrudDao.findAll().forEach(users::add);


        return ResponseEntity.ok(users);
    }

    @GetMapping("/db/update")
    @Transactional
    @Timing
    public ResponseEntity<User> updatePasswords() {

        databaseExampleDao.massPasswordUpdate();


        return ResponseEntity.ok().build();

    }
}
