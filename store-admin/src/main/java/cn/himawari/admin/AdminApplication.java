package cn.himawari.admin;

import cn.himawari.clients.CategoryClient;
import cn.himawari.clients.SearchClient;
import cn.himawari.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = "cn.himawari.admin.mapper")
@EnableCaching
@EnableFeignClients(clients = {UserClient.class, CategoryClient.class, SearchClient.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class,args);
    }
}
