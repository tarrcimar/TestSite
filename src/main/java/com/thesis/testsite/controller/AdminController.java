package com.thesis.testsite.controller;

import com.thesis.testsite.entity.User;
import com.thesis.testsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
        Document doc = db.parse(tempFile);
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("user");
        System.out.println("----------------------------");

        System.out.println("Timer started!");
        long start = System.currentTimeMillis();
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
                System.out.println("Username : "
                        + username);
                System.out.println("Password : "
                        + password);
                System.out.println("Role : "
                        + role);
                userService.registerUser(new User(username, password, role));
                if(temp == nList.getLength()) success = true;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Timer ended. Elapsed time: " + (end-start));

        if (success)
            return "redirect:/adminPanel?success=true";
        return "redirect:/adminPanel?success=false";
    }

}
