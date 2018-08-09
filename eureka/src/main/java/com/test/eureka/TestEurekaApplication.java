package com.test.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TestEurekaApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestEurekaApplication.class).web(true).run(args);
    }
}
