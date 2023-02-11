package com.example.deal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({
        "com.example.deal.integration"
})
public class DealApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealApplication.class, args);
    }

}
