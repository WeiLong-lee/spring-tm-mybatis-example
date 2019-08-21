package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.example.mybatis.demo.mapper"})
@SpringBootApplication
public class SpringTmExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTmExampleApplication.class, args);
    }

}
