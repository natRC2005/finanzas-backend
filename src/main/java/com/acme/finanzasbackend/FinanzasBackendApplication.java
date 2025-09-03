package com.acme.finanzasbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FinanzasBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanzasBackendApplication.class, args);
    }

}
