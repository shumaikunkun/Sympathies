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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;


@org.springframework.stereotype.Controller
public class GreetingController {

    private static final Logger log = LoggerFactory.getLogger(SympathiesApplication.class);

    @Autowired
    UserRepository userRepo;
    @Autowired
    GoodsRepository goodsRepo;
    @Autowired
    TransactionRepository transactionRepo;

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
            log.info(goodsRepo.findAll().toString());
            model.addAttribute("goods", goodsRepo.findAll());
            return "main";
        }
    }

    @GetMapping("/setting")
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
                model.addAttribute("createAt", goods.getCreateAt());
                return "buy_pre";
            }
        }
        return "error";
    }

    @PostMapping("/buy")
    public String buy( Model model, @RequestParam("usr") String usr,  @RequestParam("id") String id) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す
        model.addAttribute("id", id);

        // 送信元ユーザ、送信先ユーザ、商品の取得
        List<User> destUsers = userRepo.findByMail(usr);
        if (destUsers == null || destUsers.size() == 0) {
            return "error";
        }
        User destUser = destUsers.get(0);
        Optional<Goods> itemOpt = goodsRepo.findById(Long.parseLong(id));
        if (!itemOpt.isPresent()) {
            return "error";
        }
        Goods item = itemOpt.get();
        Optional<User> senderOpt = userRepo.findById(item.getUserId());
        if (!senderOpt.isPresent()) {
            return "error";
        }
        User sender = senderOpt.get();

        // 出品者と購入者が同じときエラー
        if (item.getUserId() == destUsers.get(0).getId()) {
            return "error";
        }

        // ポイント数が足りないときエラー
        if (destUser.getPoint() - item.getPoint() < 0) {
            return "error";
        }

        // トランザクション更新の処理
        transactionRepo.save(new Transaction(item.getUserId(), destUser.getId(), item.getId()));

        // ポイントの更新の処理
        sender.setPoint(sender.getPoint() + item.getPoint());
        destUser.setPoint(destUser.getPoint() - item.getPoint());
        userRepo.save(sender);
        userRepo.save(destUser);

        return "buy";
    }

    @PostMapping("/sell_pre")
    public String sell_pre( Model model, @RequestParam("usr") String usr, @ModelAttribute("inputGoods") InputGoods inputGoods) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        return "sell_pre";
    }




    @PostMapping("/sell")
    public String sell( Model model, @RequestParam("usr") String usr, @RequestParam(name="upload_file",required=false) MultipartFile upfile, @ModelAttribute("inputGoods") InputGoods inputGoods) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        //画像ファイル追加の処理
        if (!upfile.isEmpty()) {
            try {

                byte[] bytes = upfile.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/images/"+upfile.getOriginalFilename())));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                //return error page
            }
        }

        //画像の説明の処理
        log.info(inputGoods.getName());
        log.info(inputGoods.getDescription());

        List<User> users =userRepo.findByMail(usr);


        //goodsデータベースに追加
        goodsRepo.save(new Goods( users.get(0).getId(), inputGoods.getName(), inputGoods.getDescription(), inputGoods.getPoint(), "images/"+upfile.getOriginalFilename()));
        //usrIDをメアドからとってくる

        return "sell";
    }

    @PostMapping("/history")
    public String history(Model model, @RequestParam("usr") String usr) {
        // ログインユーザの取得
        List<User> users = userRepo.findByMail(usr);
        if (users == null || users.size() == 0) {
            return "error";
        }

        // ログインユーザが購入した商品一覧の取得
        List<Goods> boughtList = new ArrayList<Goods>();
        List<Transaction> transactions = transactionRepo.findByDestinationUserId(users.get(0).getId());
        for (Transaction transaction : transactions) {
            Optional<Goods> item = goodsRepo.findById(transaction.getGoodsId());
            if (!item.isPresent()) continue;
            boughtList.add(item.get());
        }

        // modelに追加
        model.addAttribute("usr", usr);
        model.addAttribute("goods", boughtList);

        return "history";
    }
}
