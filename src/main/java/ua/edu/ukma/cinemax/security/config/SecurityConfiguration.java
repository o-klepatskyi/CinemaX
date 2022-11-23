package ua.edu.ukma.cinemax.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ua.edu.ukma.cinemax.api.controller.FilmController;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    final static Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/film/add").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.DELETE, "/film/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.POST, "/film/**").hasAuthority(ADMIN)
                .antMatchers(HttpMethod.PUT, "/film/**").hasAuthority(ADMIN)
                .antMatchers("/film/details/**").authenticated()
                .anyRequest().permitAll()
//                .antMatchers(HttpMethod.GET, "film/details/**").authenticated()

                 //                 .antMatchers("film/all", "film/{id}").hasAuthority(ADMIN)
//                 .antMatchers(HttpMethod.GET, "film/{id}").anonymous()
                 .and().httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user").password("user").authorities(USER).build();
        UserDetails admin = User.withUsername("admin").password("admin").authorities(ADMIN).build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
