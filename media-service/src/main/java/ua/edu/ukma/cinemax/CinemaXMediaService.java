package ua.edu.ukma.cinemax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ua.edu.ukma.cinemax"})
public class CinemaXMediaService {

    public static void main(String[] args) {
        SpringApplication.run(CinemaXMediaService.class, args);
        System.out.println("Started media service.");
    }
}
