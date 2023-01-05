package com.amberflo.metering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ServletComponentScan
@SpringBootApplication
@ComponentScan(basePackages = {"com.amberflo.metering"})
@Configuration
public class AmberfloExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmberfloExampleApplication.class, args);
    }
}