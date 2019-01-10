package org.hideoutgroup.user;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author 董文强
 * @version 1.0
 * @date 2018年12月18日
 */
@SpringBootApplication
@MapperScan("org.hideoutgroup.user.db.mapper")
public class RabbitConsumerApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConsumerApp.class);

    public static void main(String[] args) {
        SpringApplication.run(RabbitConsumerApp.class, args);

    }


}
