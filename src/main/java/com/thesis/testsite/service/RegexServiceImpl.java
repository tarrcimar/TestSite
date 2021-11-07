package com.thesis.testsite.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegexServiceImpl implements RegexService{
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final Pattern pPattern = Pattern.compile(PASSWORD_PATTERN);

    private static final String USERNAME_PATTERN = "^(?=.*[0-9])(?=.*[a-z]).{5,10}$";
    private static final Pattern uPattern = Pattern.compile(USERNAME_PATTERN);

    public  boolean isValidPassword(final String password){
        Matcher matcher = pPattern.matcher(password);
        return matcher.matches();
    }

    public boolean isValidUsername(final String username){
        Matcher matcher = uPattern.matcher(username);
        return matcher.matches();
    }
}
