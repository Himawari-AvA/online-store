package cn.himawari.history;

import cn.himawari.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = {ProductClient.class})
@MapperScan(basePackages ="cn.himawari.history.mapper")
@SpringBootApplication
public class HistoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(HistoryApplication.class,args);
    }
}
