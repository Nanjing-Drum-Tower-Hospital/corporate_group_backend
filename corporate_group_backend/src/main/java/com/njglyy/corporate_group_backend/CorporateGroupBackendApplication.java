package com.njglyy.corporate_group_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CorporateGroupBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorporateGroupBackendApplication.class, args);
    }

}
