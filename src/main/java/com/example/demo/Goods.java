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
    private Long userId;
    private String name;
    private String description;
    private Integer point;
    private String path;
    private Date createAt;

    protected Goods() {}

    public Goods(Long userId, String name, String description, Integer point, String path) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.point = point;
        this.path = path;
        this.createAt = new Date();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getPoint() { return point; }
    public String getPath() { return path; }
    public Date getCreateAt() { return createAt; }

    @Override
    public String toString() {
        return String.format("Goods[id='%d', userId='%d', name='%s', description='%s', point='%d', path='%s', createAt=%s]",id ,userId, name, description, point, path, createAt);
    }

}
