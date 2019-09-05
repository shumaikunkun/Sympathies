package com.example.demo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class GoodsTf {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Integer point;
    private String path;
    private Date createAt;
    private boolean isBought;





    protected GoodsTf() {}

    public GoodsTf(Long userId, String name, String description, Integer point, String path) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.point = point;
        this.path = path;
        this.createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public void copyGoods(Goods goods){
        id = goods.getId();
        userId=goods.getUserId();
        name=goods.getName();
        description=goods.getDescription();
        point=goods.getPoint();
        path=goods.getPath();
        createAt=goods.getCreateAt();
    }

    @Override
    public String toString() {
        return String.format("GoodsTf[id='%d', userId='%d', name='%s', description='%s', point='%d', path='%s', createAt='%s', isBought='%b']",id ,userId, name, description, point, path, createAt, isBought);
    }


}
