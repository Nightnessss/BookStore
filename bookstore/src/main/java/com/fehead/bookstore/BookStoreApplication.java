package com.fehead.bookstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Nightessss 2020/6/30 20:40
 */
@SpringBootApplication
@MapperScan("com.fehead.bookstore.dao.mapper")
@ServletComponentScan
@EnableSwagger2
public class BookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);
    }
}
