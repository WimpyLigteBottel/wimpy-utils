package com.wimpy.examples.db.basic;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.id.SequenceHiLoGenerator;
import org.hibernate.id.enhanced.HiLoOptimizer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class User {

    private long id;
    private String username;
    private String password;
    private Date inserted;
    private Date updated;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(length = 50, unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column()
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(nullable = false)
    public Date getInserted() {
        return inserted;
    }

    public void setInserted(Date inserted) {
        this.inserted = inserted;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(nullable = false)
    public Date getUpdated() {
        return updated;
    }


    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
