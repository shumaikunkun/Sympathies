package com.example.demo.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.example.demo.repository.UserRepository;

@Controller
public class UserController{


//	private Logger logger = LoggerFactory.getLogger(UserController.class);

//@Autowired
//public void setProductRepository(UserRepository userRepository) {
//    this.userRepository = userRepository;
//}



@RequestMapping(path = "/user",method = RequestMethod.GET)
public String User(Model model) {
	model.addAttribute("name", new com.example.demo.model.User());
    return "user";
}

}
