package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String mail;



    private String name;
    private String passward;
    private Integer point;

    protected User() {}

    public Long getId() { return id; }
    public Integer getPoint() { return point; }
    public String getName() { return name; }
    public void setPoint(Integer point) { this.point = point; }

    public User(String mail, String name, String passward, Integer point) {
        this.mail = mail;
        this.name = name;
        this.passward = passward;
        this.point = point;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, mail='%s', name='%s', passward='%s', point='%d']",
                id, mail, name, passward, point);
    }

}
