package com.thomas.EWD_PROJECT_LIBR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    @Qualifier("custom")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().and()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/login**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/403").permitAll()
                        .requestMatchers("/toevoegPage").hasRole("ADMIN")
                        .requestMatchers("/library").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/library/popularBoeks").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/library/{id}/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/books/**").permitAll())
                .formLogin(form -> form.defaultSuccessUrl("/library", true).loginPage("/login")).exceptionHandling()
                .accessDeniedPage("/403");

        return http.build();
    }
}
