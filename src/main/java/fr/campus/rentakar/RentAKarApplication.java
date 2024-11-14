package fr.campus.rentakar;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EntityScan(basePackages = "fr.campus.rentakar.model")

public class RentAKarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentAKarApplication.class, args);
    }

}
