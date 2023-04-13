package cn.himawari.user;

import cn.himawari.clients.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "cn.himawari.user.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}
