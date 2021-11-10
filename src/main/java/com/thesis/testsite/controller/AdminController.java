package com.thesis.testsite.controller;

import com.thesis.testsite.entity.User;
import com.thesis.testsite.service.RegexService;
import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
public class AdminController {

    private static final long FIRST_UPLOAD_TIME = System.currentTimeMillis();

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

    @RequestMapping("/deleteMessage/{messageId}")
    public String deleteMessage(@PathVariable(value = "messageId") Long messageId){
        System.out.println("Id: " + messageId);
        userService.deleteMessage(messageId);
        return "redirect:/";
    }

    @RequestMapping("/adminPanel")
    public String adminPanel(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("adminUser", user);
        return "adminPanel";
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String upload(@RequestParam("fileToUpload") MultipartFile document, Principal principal) throws IOException, ParserConfigurationException, SAXException {
        System.out.println("TIME: " + FIRST_UPLOAD_TIME);
        User user = userService.findByUsername(principal.getName());
        userService.increaseAttempts(user);
        if (user.getFailedAttempt() > 4 || !user.isAccount_non_locked()) userService.lock(user);
        if(document.getSize() > 800){
            if(user.isAccount_non_locked()) userService.lock(user);
            return "redirect:/adminPanel?success=2";
        }
        File tempFile = File.createTempFile("valami", ".xml");
        tempFile.deleteOnExit();
        document.transferTo(tempFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(tempFile);
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("user");
        System.out.println("----------------------------");

        //System.out.println("Timer started!");
        //long start = System.currentTimeMillis();
        boolean success = false;
        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);
            System.out.println("Current element: " + nNode.getNodeName());
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element nElement = (Element) nNode;
                String username = nElement.getElementsByTagName("username")
                        .item(0).getTextContent();
                String password = nElement.getElementsByTagName("password")
                        .item(0).getTextContent();
                String role = nElement.getElementsByTagName("role")
                        .item(0).getTextContent();
                if(username == null || password == null) return "redirect:/adminPanel?success=0";
                if(!regexService.isValidUsername(username)) return "redirect:/adminPanel?success=0";
                if(!regexService.isValidPassword(password)) return "redirect:/adminPanel?success=1";
                System.out.println("Username : "
                        + username);
                System.out.println("Password : "
                        + password);
                System.out.println("Role : "
                        + role);
                userService.registerUser(username, password, role);
                if(temp == nList.getLength()) success = true;
            }
        }
        //long end = System.currentTimeMillis();
        //System.out.println("Timer ended. Elapsed time: " + (end-start));

        if (success)
            return "redirect:/adminPanel?success=true";
        return "redirect:/adminPanel?success=false";
    }

}
