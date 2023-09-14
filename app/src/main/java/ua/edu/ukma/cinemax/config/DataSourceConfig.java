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
import ua.edu.ukma.cinemax.persistance.entity.Role;
import ua.edu.ukma.cinemax.persistance.repository.CinemaHallRepository;
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository;
import ua.edu.ukma.cinemax.persistance.repository.SessionRepository;
import ua.edu.ukma.cinemax.security.model.Roles;
import ua.edu.ukma.cinemax.service.UserService;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

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
    @Profile("dev")
    public CommandLineRunner run(@Value("${spring.jpa.hibernate.ddl-auto}") String ddl,
                                 RoleRepository roleRepository,
                                 UserService userService,
                                 CinemaHallRepository cinemaHallRepository,
                                 SessionRepository sessionRepository) {
        return (String[] args) -> {
            if (!ddl.startsWith("create")) {
                return;
            }
            for (String name : Arrays.stream(Roles.values()).map(Roles::name).toList()) {
                addRoles(roleRepository, name);
            }
            addUsers(userService);
            addCinemaHalls(cinemaHallRepository);
        };
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
}
