package ua.edu.ukma.cinemax;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAdminServer
@SpringBootApplication(scanBasePackages = {"ua.edu.ukma.cinemax"})
public class CinemaXAdminService {

    public static void main(String[] args) {
        SpringApplication.run(CinemaXAdminService.class, args);
        System.out.println("Started admin service.");
    }
}
