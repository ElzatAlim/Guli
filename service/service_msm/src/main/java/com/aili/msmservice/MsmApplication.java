package com.aili.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 艾力
 * @date 2021/1/26 11:20
 **/

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.aili")
public class MsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }

}
