package ua.edu.ukma.cinemax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = {"ua.edu.ukma.cinemax"})
public class CinemaXApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaXApplication.class, args);
    }

}
