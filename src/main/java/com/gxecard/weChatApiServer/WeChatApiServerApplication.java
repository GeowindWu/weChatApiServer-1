package com.gxecard.weChatApiServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableAutoConfiguration
@SpringBootApplication
public class WeChatApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatApiServerApplication.class, args);
    }
}
