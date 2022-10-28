package ua.edu.ukma.cinemax;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import ua.edu.ukma.cinemax.model.Film;
import ua.edu.ukma.cinemax.service.FilmService;

@SpringBootApplication
public class CinemaXApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaXApplication.class, args);
    }
//    @Bean
//    @Profile("dev")
//    public CommandLineRunner run(FilmService filmService) {
//        return (String[] args) -> {
//            Film shrek = new Film();
//            shrek.setTitle("Shrek");
//            shrek.setReleaseYear(2001);
//            shrek.setDescription("Amazing start of the 21st century!");
//            filmService.add(shrek);
//            Film test = new Film();
//            test.setTitle("Test film");
//            test.setReleaseYear(9999);
//            test.setDescription("Testing description...");
//            filmService.add(test);
//       };
//   }
}
