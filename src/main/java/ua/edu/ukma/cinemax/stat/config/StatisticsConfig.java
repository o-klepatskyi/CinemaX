package ua.edu.ukma.cinemax.stat.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import ua.edu.ukma.cinemax.stat.service.StatisticsService;

@EnableAutoConfiguration
@ConditionalOnClass(StatisticsService.class)
@ConditionalOnProperty(prefix = "stat", name = "enabled", havingValue = "true")
@ConditionalOnResource(resources = {"${stat.path}"})
public class StatisticsConfig {

}
