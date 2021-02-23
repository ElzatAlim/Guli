package com.aili.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 艾力
 * @date 2021/1/12 14:25
 **/

@SpringBootApplication
@EnableDiscoveryClient  //Nacos注册服务
@EnableFeignClients //Nacos调用服务
@ComponentScan(basePackages = {"com.aili"})
@MapperScan("com.aili.eduservice.mapper")
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}
