package com.wimpy.examples.db;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
public class DatabaseExample {

    private final DatabaseExampleCrudDao databaseExampleCrudDao;


    public DatabaseExample(DatabaseExampleCrudDao databaseExampleCrudDao) {
        this.databaseExampleCrudDao = databaseExampleCrudDao;
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
        user.setPassword(password);

        if (!databaseExampleCrudDao.findById(name).isPresent()) {
            databaseExampleCrudDao.save(user);
            Logger.info("user created!");
        }


        URI location = new URI("/db/find");
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/db/find")
    public ResponseEntity<User> find(@RequestParam String name) {

        Optional<User> byId = databaseExampleCrudDao.findById(name);


        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }

        return ResponseEntity.notFound().build();

    }
}
