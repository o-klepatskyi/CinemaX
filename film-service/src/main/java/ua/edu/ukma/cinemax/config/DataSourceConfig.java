package ua.edu.ukma.cinemax.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ua.edu.ukma.cinemax")
public class DataSourceConfig {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public CommandLineRunner run(@Value("${spring.jpa.hibernate.ddl-auto}") String ddl,
                                 FilmRepository filmRepository) {
        return (String[] args) -> {
            if (!ddl.startsWith("create")) {
                return;
            }
            addFilms(filmRepository);
        };
    }

    private void addFilms(FilmRepository filmRepository) {
        Film film = new Film();
        film.setTitle("American Psycho");
        long tmdbId = 1359L;
        film.setTmdbId(tmdbId);
        film.setDescription("A wealthy New York investment banking executive" +
                "hides his alternate psychopathic ego from his co-workers and" +
                "friends as he escalates deeper into his illogical, gratuitous fantasies.");
        int year = 2000;
        film.setReleaseYear(year);
        filmRepository.save(film);

        film = new Film();
        film.setTitle("Batman Begins");
        film.setTmdbId(272L);
        film.setDescription("Driven by tragedy, billionaire Bruce Wayne" +
                "dedicates his life to uncovering and defeating the corruption" +
                "that plagues his home, Gotham City.");
        film.setReleaseYear(2005);
        filmRepository.save(film);

        film = new Film();
        film.setTitle("Driver");
        tmdbId = 64690L;
        film.setTmdbId(tmdbId);
        film.setDescription("Driver is a skilled Hollywood stuntman who moonlights as a getaway driver for criminals.");
        year = 2011;
        film.setReleaseYear(year);
        filmRepository.save(film);
    }
}
