package com.cactus.springboot.multi.thread;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.cactus.springboot.multi.thread.**.mapper"})
public class SpringbootMultiThreadApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMultiThreadApplication.class, args);
        System.out.println("Application run success!");
    }

}
