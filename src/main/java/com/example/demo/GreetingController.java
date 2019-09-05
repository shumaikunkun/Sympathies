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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

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
    public String main(Model model, @ModelAttribute("inputUsr") InputUsr inputUsr, @RequestParam(name="usr",required=false) String usr ) {

        //model.addAttribute("name",inputUsr.getUsr());
        //usr=inputUsr.getUsr();
        //model.addAttribute("usr",usr);

        log.info("-------"+ usr);

        model.addAttribute("usr", usr!=null ? usr : inputUsr.getMail());

        // fetch an individual user by ID

        List<User> user = userRepo.findByMailAndPassward(inputUsr.getMail(), inputUsr.getPass());
        if ((user == null || user.size() == 0 ) && usr==null) {

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


    @PostMapping("/buy_pre")
    public String buy_pre(Model model, @RequestParam("usr") String usr,  @RequestParam("id") String id) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す
        model.addAttribute("id", id);

        for (Goods goods : goodsRepo.findAll()) {

            if (goods.getId() == Long.parseLong(id)) {
                model.addAttribute("id", goods.getId());
                model.addAttribute("name", goods.getName());
                model.addAttribute("description", goods.getDescription());
                model.addAttribute("point", goods.getPoint());
                model.addAttribute("path", goods.getPath());
                model.addAttribute("create_at", goods.getCreate_at());
                return "buy_pre";
            }
        }
        return "error";
    }

    @PostMapping("/buy")
    public String buy( Model model, @RequestParam("usr") String usr,  @RequestParam("id") String id) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す
        model.addAttribute("id", id);

        //ポイントの更新の処理
        //トランザクション更新の処理

        return "buy";
    }

    @PostMapping("/sell_pre")
    public String sell_pre( Model model, @RequestParam("usr") String usr) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        return "sell_pre";
    }




    @PostMapping("/sell")
    public String sell( Model model, @RequestParam("usr") String usr, @RequestParam("upload_file") MultipartFile upfile) {

        if (!upfile.isEmpty()) {
            try {
                //cm.setImgUrl(upfile.getOriginalFilename());
                byte[] bytes = upfile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/images/"+"1.jpg")));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                //return error page
            }
        }


        //model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        //画像ファイル追加の処理
        model.addAttribute("originalFilename", upfile.getOriginalFilename());

        //goodsデータベースに追加

        return "sell";
    }



}

