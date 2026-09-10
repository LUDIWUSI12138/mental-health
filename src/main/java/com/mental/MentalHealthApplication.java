package com.mental;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mental.mapper")
public class MentalHealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(MentalHealthApplication.class, args);
    }
}
