package com.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Happy
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
@Slf4j
public class NettyStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyStudyApplication.class, args);
        log.info("NettyStudy启动了!哈哈哈!!!");
    }

}
