package com.thesis.testsite.controller;

import com.thesis.testsite.entity.Message;
import com.thesis.testsite.entity.User;
import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/msg")
    public String addMessage(@RequestParam("content") String content, Principal principal){
        userService.addNewMessage(principal.getName() ,content);
        return "redirect:/";
    }

}
