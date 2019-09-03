package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String id;
    private String name;
    private String passward;
    private Integer point;

    protected User() {}

    public User(String id, String name, String passward, Integer point) {
        this.id = id;
        this.name = name;
        this.passward = passward;
        this.point = point;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id='%s', name='%s', passward='%s', point='%d']",
                id, name, passward, point);
    }

}
