package com.aili.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 艾力
 * @date 2021/2/3 14:13
 **/
@SpringBootApplication
@EnableDiscoveryClient  //Nacos注册服务
@EnableFeignClients //Nacos调用服务
@ComponentScan(basePackages = {"com.aili"})
@MapperScan("com.aili.eduorder.mapper")
public class OrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}
