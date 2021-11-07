package com.thesis.testsite.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RegexService {

    boolean isValidPassword(final String password);

    boolean isValidUsername(final String username);
}
