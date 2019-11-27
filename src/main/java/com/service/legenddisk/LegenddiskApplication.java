package com.service.legenddisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.service.legenddisk.mapper")
public class LegenddiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(LegenddiskApplication.class, args);
    }

}
