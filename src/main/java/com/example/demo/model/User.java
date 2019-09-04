package com.example.demo.model;

import javax.persistence.Entity;

@Entity
public class User{

	    private String name;


	    public User() {
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = "井上";
	    }
}