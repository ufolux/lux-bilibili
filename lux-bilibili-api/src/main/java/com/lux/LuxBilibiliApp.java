package com.lux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LuxBilibiliApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(LuxBilibiliApp.class, args);
    }
}
