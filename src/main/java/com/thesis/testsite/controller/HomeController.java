package com.thesis.testsite.controller;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;
import com.thesis.testsite.service.LogService;
import com.thesis.testsite.service.RegexService;
import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private RegexService regexService;

    @Autowired
    public void setRegexService(RegexService regexService) {
        this.regexService = regexService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private LogService logService;

    @Autowired
    public void setLogService(LogService logService) {
        this.logService = logService;
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
        logService.createLog("User: " + principal.getName() + "sent a new message.", "INFO");
        return "redirect:/";
    }

    @PostMapping("/reg")
    public String registerUser(@RequestParam("username") String username, @RequestParam("password") String password,
                           @RequestParam("repeatPassword") String repeatPassword){

        if(!regexService.isValidUsername(username)) return "redirect:/register?flag=1";

        if(password.equals(repeatPassword)){
            if(regexService.isValidPassword(password)){
                userService.registerUser(username, password, "user");
                logService.createLog("User registered: " + username, "INFO");
                return "redirect:/register?flag=true";
            }
            else
                return "redirect:/register?flag=0";
        }

        logService.createLog("User registration failed: " + username, "WARN");
        return "redirect:/register?flag=false";
    }

}
