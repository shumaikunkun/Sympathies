package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Goods {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long user_id;
    private String name;
    private String description;
    private Integer point;
    private String path;
    private Date create_at;

    protected Goods() {}

    public Goods(Long user_id, String name, String description, Integer point, String path) {
        this.user_id = user_id;
        this.name = name;
        this.description = description;
        this.point = point;
        this.path = path;
        this.create_at = new Date();
    }

    @Override
    public String toString() {
        return String.format("Goods[id='%d', user_id='%d', name='%s', description='%s', point='%d', path='%s', create_at=%s]",id ,user_id, name, description, point, path, create_at);
    }

}
