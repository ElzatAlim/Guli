package com.aili.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 艾力
 * @date 2021/1/15 19:38
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})     //exclude数据库配置避免报错
@EnableDiscoveryClient
@ComponentScan({"com.aili"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
