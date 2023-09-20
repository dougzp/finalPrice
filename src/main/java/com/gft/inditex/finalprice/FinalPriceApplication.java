package com.gft.inditex.finalprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@SpringBootApplication(scanBasePackages = {"com.gft.inditex.finalprice", "com.gft.inditex.persistence"})
@EnableR2dbcRepositories(basePackages = "com.gft.inditex.persistence")
public class FinalPriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalPriceApplication.class, args);
    }

}
