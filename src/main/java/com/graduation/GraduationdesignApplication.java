package com.graduation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;


@ServletComponentScan(basePackages = "com.graduation")
@RestController
@SpringBootApplication
public class GraduationdesignApplication {

    public static void main(String[] args) {

        SpringApplication.run(GraduationdesignApplication.class, args);

    }

}
