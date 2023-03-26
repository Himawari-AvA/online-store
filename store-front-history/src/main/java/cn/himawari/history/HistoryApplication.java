package cn.himawari.history;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class HistoryApplication {

    @MapperScan(basePackages = "cn.himawari.history.mapper")
    @SpringBootApplication
    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class,args);
    }
}
