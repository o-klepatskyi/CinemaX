package ua.edu.ukma.cinemax.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ua.edu.ukma.cinemax.security.filter.FilmDetailsAuthFilter;
import static ua.edu.ukma.cinemax.security.model.Roles.ADMIN;
import static ua.edu.ukma.cinemax.security.model.Roles.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .addFilterAfter(new FilmDetailsAuthFilter(), BasicAuthenticationFilter.class) // BasicAuthenticationFilter.class
                .authorizeRequests()
                .antMatchers("/film/add").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/film/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.POST, "/film/**").hasAuthority(ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/film/**").hasAuthority(ADMIN.name())
                .antMatchers("/film/details/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user").password("user").authorities(USER.name()).build();
        UserDetails admin = User.withUsername("admin").password("admin").authorities(ADMIN.name()).build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
