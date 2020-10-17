package com.wimpy.examples.db.hilo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class DatabaseExampleHiLoRest {

    @Autowired
    ItemCrudDao itemCrudDao;


    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/db/item")
    @Transactional
    public void generateRandomItem() {

        Item item = new Item();
        item.setItemName(UUID.randomUUID().toString());
        double randomValue = ThreadLocalRandom.current().nextDouble(0, 100);
        item.setPrice(BigDecimal.valueOf(randomValue));


        entityManager.persist(item);
    }

    @GetMapping("/db/createItems")
    @Transactional
    public void generateBatchOfItems() {


        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Item item = new Item();
            item.setItemName(UUID.randomUUID().toString());
            double randomValue = ThreadLocalRandom.current().nextDouble(0, 100);
            item.setPrice(BigDecimal.valueOf(randomValue));
            items.add(item);

        }

        itemCrudDao.saveAll(items);

    }

    @GetMapping("/db/findItem")
    public Item findItem(@RequestParam Long id) {
        return entityManager.find(Item.class, id);
    }

    @GetMapping("/db/findAllItems")
    public List findItems() {
        return entityManager.createQuery("from Item i", Item.class).getResultList();
    }
}
