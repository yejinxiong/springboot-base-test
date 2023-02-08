package com.cactus.springboot.mysql.batch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"com.cactus.springboot.mysql.batch.**.mapper"})
public class SpringbootMysqlBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMysqlBatchApplication.class, args);
        System.out.println("Application run success!");
    }

}
