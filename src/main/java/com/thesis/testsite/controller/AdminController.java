package com.thesis.testsite.controller;

import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @RequestMapping("/adminPanel")
    public String adminPanel(){
        return "adminPanel";
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(@RequestParam("fileToUpload") MultipartFile document) throws IOException, ParserConfigurationException, SAXException {
        File tempFile = File.createTempFile("valami", ".xml");
        tempFile.deleteOnExit();
        document.transferTo(tempFile);
        System.out.println("transfered");
        System.out.println(document);
        System.out.println(tempFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document1 = db.parse(tempFile);
        System.out.println(document1.toString());

        return "redirect:/adminPanel";
    }

}
