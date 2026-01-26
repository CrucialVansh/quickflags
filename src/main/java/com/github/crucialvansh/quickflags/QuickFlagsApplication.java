package com.github.crucialvansh.quickflags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuickFlagsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickFlagsApplication.class, args);
    }

}
