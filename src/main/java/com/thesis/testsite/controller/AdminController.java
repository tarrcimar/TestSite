package com.thesis.testsite.controller;

import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/deleteMessage/{messageId}")
    public String deleteMessage(@PathVariable(value = "messageId") Long messageId){
        System.out.println("Id: " + messageId);
        userService.deleteMessage(messageId);
        return "redirect:/";
    }

}
