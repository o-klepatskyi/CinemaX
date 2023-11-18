package ua.edu.ukma.cinemax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"ua.edu.ukma.cinemax"})
public class CinemaXApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CinemaXApplication.class, args);
        System.out.println("Welcome to cinemaX!");
    }
}
