package com.thesis.testsite.controller;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;
import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String home(Model model){
        List<Message> messages = userService.getMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("newMessage", new Message());
        return "index";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/msg", method = {RequestMethod.POST, RequestMethod.GET})
    public String addMessage(@RequestParam("content") String content, Principal principal){
        //content = content.replaceAll("[^a-zA-Z0-9]", " ");
        userService.addNewMessage(principal.getName() ,content);
        return "redirect:/";
    }

    @PostMapping("/reg")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password,
                           @RequestParam("repeatPassword") String repeatPassword){
        //password = password.replaceAll("[^a-zA-Z0-9]", " ");
        //username = username.replaceAll("[^a-zA-Z0-9]", " ");
        //repeatPassword = repeatPassword.replaceAll("[^a-zA-Z0-9]", " ");
        if(password.equals(repeatPassword)){
            userService.registerUser(new User(username, password, "user"));
            return "redirect:/register?flag=true";
        }

        return "redirect:/register?flag=false";

    }

}
