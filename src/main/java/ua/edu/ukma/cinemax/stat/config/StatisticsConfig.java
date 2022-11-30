package ua.edu.ukma.cinemax.stat.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Configuration;
import ua.edu.ukma.cinemax.stat.service.StatisticsService;

@Configuration
@ConditionalOnClass(StatisticsService.class)
@ConditionalOnProperty(prefix = "stat", name = "enabled", havingValue = "true")
@ConditionalOnResource(resources = {"${stat.path}"})
public class StatisticsConfig {

}
