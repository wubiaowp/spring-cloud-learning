package com.test.zipkin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class TestZipkinApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestZipkinApplication.class).web(true).run(args);
    }
}
