package ua.edu.ukma.cinemax.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua.edu.ukma.cinemax.security.config.CustomAccessDeniedHandler;
import ua.edu.ukma.cinemax.security.model.UserDetailsServiceImpl;

import static ua.edu.ukma.cinemax.security.model.Roles.ADMIN;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .anyRequest().authenticated()
//                .antMatchers("/index").permitAll()
//                .antMatchers("/users").hasRole("ADMIN")
                .and().
                formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll())
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
//                .antMatchers("/register/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                .antMatchers(HttpMethod.DELETE).hasAnyAuthority(ADMIN.name())
//                .antMatchers(HttpMethod.POST).hasAnyAuthority(ADMIN.name())
//                .antMatchers(HttpMethod.PUT).hasAnyAuthority(ADMIN.name())
//                .antMatchers("/**/add").hasAnyAuthority(ADMIN.name())
//                //.antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/login")
//                    .defaultSuccessUrl("/users", true)
//                    .permitAll()
//                .and()
//                .logout().permitAll()

    }

}
