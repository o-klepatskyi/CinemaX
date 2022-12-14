package ua.edu.ukma.cinemax.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.entity.Role;
import ua.edu.ukma.cinemax.persistance.repository.RoleRepository;
import ua.edu.ukma.cinemax.security.model.Roles;
import ua.edu.ukma.cinemax.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public CommandLineRunner run(RoleRepository roleRepository, UserService userService) {
        return (String[] args) -> {
            for (String name : Arrays.stream(Roles.values()).map(Roles::name).collect(Collectors.toList()))
                addRoleIfNotExists(roleRepository, name);
            addUsersIfNotExist(userService);

       };
   }

   private void addRoleIfNotExists(RoleRepository roleRepository, String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleRepository.save(role);
        }
   }

   private void addUsersIfNotExist(UserService userService) {
       if (userService.getByUsername("user") == null) {
           UserDto user = new UserDto();
           user.setUsername("user");
           user.setEmail("user@cinemax.com");
           user.setPassword("user");
           user.setRoles(List.of(Roles.USER.name()));
           userService.add(user);
       }
        if (userService.getByUsername("admin") == null) {
            UserDto admin = new UserDto();
            admin.setUsername("admin");
            admin.setEmail("admin@cinemax.com");
            admin.setPassword("admin");
            admin.setRoles(List.of(Roles.ADMIN.name(), Roles.USER.name()));
            userService.add(admin);
        }
   }
}