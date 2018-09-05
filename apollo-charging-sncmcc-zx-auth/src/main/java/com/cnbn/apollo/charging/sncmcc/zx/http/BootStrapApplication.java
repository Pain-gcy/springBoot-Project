package com.cnbn.apollo.charging.sncmcc.zx.http;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author guochunyuan
 * @date 2018年9月3日16:50:44
 */

@SpringBootApplication
@ComponentScan(value = {"com.cnbn"})
@MapperScan(value = {"com.cnbn.apollo.charging.sncmcc.zx.dao"})
@EnableTransactionManagement
public class BootStrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootStrapApplication.class, args);
    }
}
