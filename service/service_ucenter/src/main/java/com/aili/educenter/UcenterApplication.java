package com.aili.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 艾力
 * @date 2021/1/27 16:49
 **/
@SpringBootApplication
@EnableDiscoveryClient //启动nacos注册
@EnableFeignClients //远程调用
@ComponentScan("com.aili")
@MapperScan("com/aili/educenter/mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
