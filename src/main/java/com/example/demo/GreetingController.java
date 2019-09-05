package com.example.demo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(SympathiesApplication.class);

    @Autowired
    UserRepository userRepo;
    @Autowired
    GoodsRepository goodsRepo;

    //String usr;

    @GetMapping("/")
    public String login(@ModelAttribute("inputUsr") InputUsr inputUsr) {
        return "login";
    }

    @PostMapping(path="/main")
    public String main(@ModelAttribute("inputUsr") InputUsr inputUsr) {

        //model.addAttribute("name",inputUsr.getUsr());
        //usr=inputUsr.getUsr();
        //model.addAttribute("usr",usr);

        // fetch an individual user by ID
        List<User> user = userRepo.findByMailAndPassward(inputUsr.getUsr(), inputUsr.getPass());
        if (user == null || user.size() == 0) {
            log.info("FALSE");
            return "login";
        } else {
            log.info("TRUE");
            return "main";
        }

    }

    @PostMapping("/setting")
    public String setting( Model model, @RequestParam("usr") String usr) {
        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す
        return "setting";
    }

    @GetMapping(path="/detail")
    public String detail(@RequestParam("id") String id) {
        for (Goods goods : goodsRepo.findAll()) {
            if (goods.getId() == Long.parseLong(id)) {
                return "detail";
            }
        }
        return "detail";
    }

}
