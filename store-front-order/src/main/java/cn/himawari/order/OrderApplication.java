package cn.himawari.order;


import cn.himawari.clients.ProductClient;
import cn.himawari.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = "cn.himawari.order.mapper")
@EnableFeignClients(clients = {ProductClient.class, UserClient.class})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
