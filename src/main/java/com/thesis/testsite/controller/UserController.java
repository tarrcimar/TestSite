package com.thesis.testsite.controller;

import com.thesis.testsite.service.RegexService;
import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }

    @RequestMapping("/modify")
    public String modify(@RequestParam(value = "password") String password,
                         @RequestParam(value = "repeatPassword") String repeatPassword,
                         Principal principal){

        if(password.equals(repeatPassword)){
            userService.updatePassword(principal.getName(), password);
            return "redirect:/profile?flag=true";
        }

        return "redirect:/profile?flag=false";
    }


}
