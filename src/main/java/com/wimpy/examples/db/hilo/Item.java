package com.wimpy.examples.db.hilo;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class Item {

    private long id;
    private String itemName;
    private BigDecimal price;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemIdHiLo")
    @SequenceGenerator(name = "itemIdHiLo", allocationSize = 1000)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(length = 100, nullable = false)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
