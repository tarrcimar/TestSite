package com.thesis.testsite.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class LogService {

    public void createLog(String message, String logLevel) {
        Logger logger = Logger.getLogger("UserActivity");
        FileHandler fh;

        try {
            fh = new FileHandler("src/main/resources/logs/UserActivity.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            if(logLevel.equals("WARN"))
                logger.warning(message);
            else
                logger.info(message);

            fh.close();
        } catch (SecurityException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
