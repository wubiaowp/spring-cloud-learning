package com.test.customer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestCustomerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestCustomerApplication.class).web(true).run(args);
    }

}
