package ua.edu.ukma.cinemax;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"ua.edu.ukma.cinemax"})
@EnableFeignClients
public class CinemaXApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CinemaXApplication.class, args);
        System.out.println("Welcome to cinemaX!");
    }

}
