package com.test.fegin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TestFeginApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestFeginApplication.class).web(true).run(args);
    }
}
