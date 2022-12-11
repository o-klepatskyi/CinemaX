package ua.edu.ukma.cinemax.config;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ua.edu.ukma.cinemax.dto.UserDto;
import ua.edu.ukma.cinemax.persistance.model.User;
import ua.edu.ukma.cinemax.service.UserService;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "ua.edu.ukma.cinemax")
public class DataSourceConfig {
    final static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

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

//    @Bean
//    @Profile("dev")
//    public CommandLineRunner run(UserService userService) {
//        return (String[] args) -> {
//            for (UserDto u : userService.getAll()) {
//                logger.info(u.toString());
//            }
//       };
//   }
}