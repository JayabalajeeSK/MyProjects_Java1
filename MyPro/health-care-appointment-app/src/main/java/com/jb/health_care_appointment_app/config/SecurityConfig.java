package com.jb.health_care_appointment_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/doctor/**").hasRole("DOCTOR")
                .requestMatchers("/api/patient/**").hasRole("PATIENT")
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); 

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                                .password("{noop}admin")
                                .roles("ADMIN")
                                .build();

        UserDetails doctor = User.withUsername("doctor")
                                 .password("{noop}doc")
                                 .roles("DOCTOR")
                                 .build();

        UserDetails patient = User.withUsername("patient")
                                  .password("{noop}pat")
                                  .roles("PATIENT")
                                  .build();

        return new InMemoryUserDetailsManager(admin, doctor, patient);
    }
}
