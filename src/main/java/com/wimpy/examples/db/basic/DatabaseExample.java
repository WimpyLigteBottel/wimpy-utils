package com.wimpy.examples.db.basic;

import com.wimpy.aop.annotations.Timing;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Transactional
    public ResponseEntity<Object> createUserInTable(@RequestParam String name, @RequestParam String password) throws URISyntaxException {

        User user = new User();
        user.setUsername(name);
        user.setPassword(passwordEncoder.encode(password));

        databaseExampleCrudDao.save(user);


        URI location = new URI("/db/find");
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/db/creates/v1")
    @Transactional
    @Timing
    public ResponseEntity<Object> createUserInTableV1() {


        databaseExampleDao.batchInsert(batchOfUsers());

        return ResponseEntity.noContent().build();
    }

    private List<User> batchOfUsers() {

        List<User> users = new ArrayList<>();

        Optional<User> lastUser = databaseExampleDao.findLastUser();

        int min = 0;
        if (lastUser.isPresent()) {
            min += lastUser.get().getId();
        }

        int max = min + 9999;

        for (int i = min; i < max; i++) {
            User e = new User();
            e.setUsername(i + "");
            e.setPassword(i + "");//technically i should encode the password but i not doing it to save time
            e.setInserted(new Date());
            e.setUpdated(new Date());
            users.add(e);
        }

        return users;
    }


    @GetMapping("/db/find")
    public ResponseEntity<User> find(@RequestParam String name) {

        Optional<User> user = databaseExampleDao.find(name);


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
