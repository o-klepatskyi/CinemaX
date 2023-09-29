package ua.edu.ukma.cinemax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = {"ua.edu.ukma.cinemax"})
public class CinemaXApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CinemaXApplication.class, args);
        openHomePage();
        System.out.println("Welcome to cinemaX!");
    }

    private static void openHomePage() throws IOException {
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/index");
    }
}
