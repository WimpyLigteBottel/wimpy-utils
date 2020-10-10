package com.wimpy.examples.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class User {

    private String username;
    private String password;
    private Date inserted;
    private Date updated;


    @Column(length = 50, unique = true, nullable = false)
    @Id
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
