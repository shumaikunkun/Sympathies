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

        model.addAttribute("usr", usr!=null ? usr : inputUsr.getMail());

        // fetch an individual user by ID

        List<User> user = userRepo.findByMailAndPassward(inputUsr.getMail(), inputUsr.getPass());
        if ((user == null || user.size() == 0 ) && usr==null) {

            log.info("ログイン＝＞ FALSE");
            return "login";
        } else {
            log.info("ログイン＝＞ TRUE");

            List<Goods> goods = goodsRepo.findAll();
            log.info("グッズ一覧＝＞ "+goods.toString());

            // 全商品リストに対し、ログインユーザが購入しているかどうかという情報を持たせたgoodsTsのリストを作成
            List<GoodsTf> goodsTf = new ArrayList<GoodsTf>();
            log.info(usr!=null ? usr : inputUsr.getMail());

            List<User> users = userRepo.findByMail(usr!=null ? usr : inputUsr.getMail());
            if (users == null || users.size() == 0) {
                return "error";
            }
            String userName = users.get(0).getName(); //ユーザ名
            int userPoint = users.get(0).getPoint(); //保有ポイント
            model.addAttribute("name", userName);
            model.addAttribute("point", userPoint);


            Long userId = users.get(0).getId(); //ユーザID
            for (Goods item : goods) {
                GoodsTf itemTf = new GoodsTf();
                itemTf.copyGoods(item);
                if (item.getUserId() == userId) {
                    // ログインユーザの出品物はTRUE
                    itemTf.setBought(true);
                    goodsTf.add(itemTf);
                    continue;
                }
                List<Transaction> tran = transactionRepo.findByDestinationUserIdAndGoodsId(userId, item.getId());
                if (tran == null || tran.size() == 0) {
                    itemTf.setBought(false);
                } else {
                    itemTf.setBought(true);
                }
                goodsTf.add(itemTf);
            }

            model.addAttribute("goods", goodsTf);

            return "main";
        }
    }

    @GetMapping("/setting")
    public String setting( Model model, @RequestParam("usr") String usr) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        List<User> users = userRepo.findByMail(usr);
        String userName = users.get(0).getName(); //ユーザ名
        int userPoint = users.get(0).getPoint(); //保有ポイント
        model.addAttribute("name", userName);
        model.addAttribute("point", userPoint);

        return "setting";
    }


    @PostMapping("/buy_pre")
    public String buy_pre(Model model, @RequestParam("usr") String usr,  @RequestParam("id") String id) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す
        model.addAttribute("id", id);

        List<User> users = userRepo.findByMail(usr);
        String userName = users.get(0).getName(); //ユーザ名
        int userPoint = users.get(0).getPoint(); //保有ポイント
        model.addAttribute("userName", userName);
        model.addAttribute("userPoint", userPoint);

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
        log.info("===error0");
        return "error";
    }

    @PostMapping("/buy")
    public String buy( Model model, @RequestParam("usr") String usr,  @RequestParam("id") String id) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す
        model.addAttribute("id", id);

        // 送信元ユーザ、送信先ユーザ、商品の取得
        List<User> destUsers = userRepo.findByMail(usr);
        if (destUsers == null || destUsers.size() == 0) {
            log.info("===error1");
            return "error";
        }
        User destUser = destUsers.get(0);
        Optional<Goods> itemOpt = goodsRepo.findById(Long.parseLong(id));
        if (!itemOpt.isPresent()) {
            log.info("===error2");
            return "error";
        }
        Goods item = itemOpt.get();
        Optional<User> senderOpt = userRepo.findById(item.getUserId());
        if (!senderOpt.isPresent()) {
            log.info("===error3");
            return "error";
        }
        User sender = senderOpt.get();

        // 出品者と購入者が同じときエラー
        if (item.getUserId() == destUsers.get(0).getId()) {
            log.info("===error4");
            return "error";
        }

        // ポイント数が足りないときエラー
        if (destUser.getPoint() - item.getPoint() < 0) {
            log.info("===error5");
            return "error";
        }

        // トランザクション更新の処理
        transactionRepo.save(new Transaction(item.getUserId(), destUser.getId(), item.getId()));

        // ポイントの更新の処理
        sender.setPoint(sender.getPoint() + item.getPoint());
        destUser.setPoint(destUser.getPoint() - item.getPoint());
        userRepo.save(sender);
        userRepo.save(destUser);

        List<User> users = userRepo.findByMail(usr);
        String userName = users.get(0).getName(); //ユーザ名
        int userPoint = users.get(0).getPoint(); //保有ポイント
        model.addAttribute("name", userName);
        model.addAttribute("point", userPoint);

        return "buy";
    }


    @PostMapping("/sell_pre")
    public String sell_pre( Model model, @RequestParam("usr") String usr, @ModelAttribute("inputGoods") InputGoods inputGoods) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        List<User> users = userRepo.findByMail(usr);
        String userName = users.get(0).getName(); //ユーザ名
        int userPoint = users.get(0).getPoint(); //保有ポイント
        model.addAttribute("name", userName);
        model.addAttribute("point", userPoint);

        return "sell_pre";
    }




    @PostMapping("/sell")
    public String sell( Model model, @RequestParam("usr") String usr, @RequestParam(name="upload_file",required=false) MultipartFile upfile, @ModelAttribute("inputGoods") InputGoods inputGoods) {

        model.addAttribute("usr", usr);  //クエリからとってきてビューに受け渡す

        List<User> users = userRepo.findByMail(usr);
        String userName = users.get(0).getName(); //ユーザ名
        int userPoint = users.get(0).getPoint(); //保有ポイント
        model.addAttribute("name", userName);
        model.addAttribute("point", userPoint);



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

        String userName = users.get(0).getName(); //ユーザ名
        int userPoint = users.get(0).getPoint(); //保有ポイント
        model.addAttribute("name", userName);
        model.addAttribute("point", userPoint);

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
