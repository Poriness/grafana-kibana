package com.agh.bulkazbananem.usersservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class UsersServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(UsersServiceApplication.class, args);
        log.info("Auth service has started!");
    }




}
