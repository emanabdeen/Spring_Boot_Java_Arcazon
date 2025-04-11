package com.conestoga.arcazon;

import com.conestoga.arcazon.repository.CategoryRepository;
import com.conestoga.arcazon.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ArcazonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArcazonApplication.class, args);


    }

}
