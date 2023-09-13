package ua.edu.ukma.cinemax.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.entity.CinemaHall;
import ua.edu.ukma.cinemax.persistance.entity.Film;
import ua.edu.ukma.cinemax.persistance.entity.Role;
import ua.edu.ukma.cinemax.persistance.entity.Session;
import ua.edu.ukma.cinemax.persistance.repository.CinemaHallRepository;
import ua.edu.ukma.cinemax.persistance.repository.FilmRepository;
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository;
import ua.edu.ukma.cinemax.persistance.repository.SessionRepository;
import ua.edu.ukma.cinemax.security.model.Roles;
import ua.edu.ukma.cinemax.service.UserService;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ua.edu.ukma.cinemax")
public class DataSourceConfig {

    private static final LocalDate SESSION_DATE = LocalDate.of(2022, 12, 10);
    private static final LocalTime SESSION_TIME = LocalTime.of(15, 0, 0);
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
    @Profile("dev")
    public CommandLineRunner run(@Value("${spring.jpa.hibernate.ddl-auto}") String ddl,
                                 RoleRepository roleRepository,
                                 UserService userService,
                                 FilmRepository filmRepository,
                                 CinemaHallRepository cinemaHallRepository,
                                 SessionRepository sessionRepository) {
        return (String[] args) -> {
            if (!ddl.startsWith("create")) {
                return;
            }
            for (String name : Arrays.stream(Roles.values()).map(Roles::name).collect(Collectors.toList())) {
                addRoles(roleRepository, name);
            }
            addUsers(userService);
            addFilms(filmRepository);
            addCinemaHalls(cinemaHallRepository);
            addSessions(sessionRepository, filmRepository, cinemaHallRepository);
        };
    }

    private void addSessions(SessionRepository sessionRepository,
                             FilmRepository filmRepository,
                             CinemaHallRepository cinemaHallRepository) {
        Session session = new Session();
        session.setFilm(filmRepository.getReferenceById(1L));
        session.setCinemaHall(cinemaHallRepository.getReferenceById(1L));
        session.setDate(SESSION_DATE);
        session.setTime(SESSION_TIME);
        sessionRepository.save(session);
        session = new Session();
        session.setFilm(filmRepository.getReferenceById(2L));
        session.setCinemaHall(cinemaHallRepository.getReferenceById(2L));
        session.setDate(SESSION_DATE);
        session.setTime(SESSION_TIME);
        sessionRepository.save(session);
    }

    private void addCinemaHalls(CinemaHallRepository cinemaHallRepository) {
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setName("Hall A");
        cinemaHall.setDescription("Small hall");
        cinemaHall.setAisles(5);
        cinemaHall.setSeatsPerAisle(5);
        cinemaHallRepository.save(cinemaHall);
        cinemaHall = new CinemaHall();
        cinemaHall.setName("Hall B");
        cinemaHall.setDescription("Big hall");
        cinemaHall.setAisles(12);
        cinemaHall.setSeatsPerAisle(20);
        cinemaHallRepository.save(cinemaHall);
    }

    private void addRoles(RoleRepository roleRepository, String name) {
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
    }

    private void addUsers(UserService userService) {
        UserDto user = new UserDto();
        user.setUsername("user");
        user.setEmail("user@cinemax.com");
        user.setPassword("user");
        user.setRoles(List.of(Roles.USER.name()));
        userService.add(user);
        UserDto admin = new UserDto();
        admin.setUsername("admin");
        admin.setEmail("admin@cinemax.com");
        admin.setPassword("admin");
        admin.setRoles(List.of(Roles.ADMIN.name(), Roles.USER.name()));
        userService.add(admin);
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
