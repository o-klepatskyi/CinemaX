package ua.edu.ukma.cinemax.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import static ua.edu.ukma.cinemax.security.model.Roles.ADMIN;
import static ua.edu.ukma.cinemax.security.model.Roles.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    public SecurityConfiguration(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/**").hasRole(ADMIN.name())
//                .and().httpBasic();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.DELETE).hasRole(ADMIN.name())
                .antMatchers(HttpMethod.POST).hasRole(ADMIN.name())
                .antMatchers(HttpMethod.PUT).hasRole(ADMIN.name())
                .antMatchers("/**/add").hasRole(ADMIN.name())
                .antMatchers("/").permitAll()
                .anyRequest().authenticated().and().httpBasic();
        // TODO how to make login page show only for html page request and not /api/** ???

//                .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .defaultSuccessUrl("/film/all", true)
//                    .permitAll()
//                .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user").roles(USER.name())
                .and()
                .withUser("admin").password("admin").roles(ADMIN.name());
    }

    //    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .csrf().disable()
//                .addFilterAfter(new FilmDetailsAuthFilter(), BasicAuthenticationFilter.class)// BasicAuthenticationFilter.class
//                .authorizeRequests()
//                .antMatchers("/film/add").hasAuthority(ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/film/**").hasAuthority(ADMIN.name())
//                .antMatchers(HttpMethod.POST, "/film/**").hasAuthority(ADMIN.name())
//                .antMatchers(HttpMethod.PUT, "/film/**").hasAuthority(ADMIN.name())
//                .antMatchers("/film/details/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").permitAll()
//                .and().httpBasic();
//        return http.build();
//    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withUsername("user").password("user").authorities(USER.name()).build();
//        UserDetails admin = User.withUsername("admin").password("admin").authorities(ADMIN.name()).build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

//    @Bean
//    public static PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}
