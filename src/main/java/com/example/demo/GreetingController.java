package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {


    @GetMapping("/")
    public String hello(@ModelAttribute("inputUsr") InputUsr inputUsr) {
        return "hello";
    }

    @PostMapping(path="/greeting")
    public String greeting(@ModelAttribute("inputUsr") InputUsr inputUsr) {
        return "greeting";
    }






}