package com.test.provider;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestProviderApplication.class).web(true).run(args);
    }

}
