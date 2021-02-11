package com.aili.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Schedules;

/**
 * @author 艾力
 * @date 2021/2/6 20:55
 **/

@SpringBootApplication
@EnableDiscoveryClient  //Nacos注册服务
@EnableFeignClients //Nacos调用服务
@ComponentScan(basePackages = {"com.aili"})
@MapperScan("com.aili.statistics.mapper")
@EnableScheduling
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class, args);
    }
}
