package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(SympathiesApplication.class);


    @Autowired
    UserRepository repository;

    @GetMapping("/")
    public String hello(@ModelAttribute("inputUsr") InputUsr inputUsr) {
        return "login";
    }

    @PostMapping(path="/main")
    public String match(@ModelAttribute("inputUsr") InputUsr inputUsr) {

        // fetch an individual user by ID
        List<User> user = repository.findByMailAndPassward(inputUsr.getUsr(), inputUsr.getPass());
        if (user == null || user.size() == 0) {
            log.info("FALSE");
            return "login";

        } else {
            log.info("TRUE");
            return "main";
        }

        // System.out.println(inputUsr.getUsr());

    }






}