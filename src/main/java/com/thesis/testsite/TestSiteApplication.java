package com.thesis.testsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@SpringBootApplication
public class TestSiteApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(TestSiteApplication.class, args);
    }

}
