package com.jb.todo_list_springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // For Method Security
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth // COMMENT For Method Security
                .requestMatchers(HttpMethod.POST, "api/admin/**").hasRole("ADMIN") // "api/** ", // COMMENT For Method Security
                .requestMatchers("api/tasks/**").hasRole("ADMIN") // COMMENT For Method Security
                .requestMatchers("api/user/**").hasAnyRole("USER", "ADMIN") // COMMENT For Method Security
                .anyRequest().authenticated() // COMMENT For Method Security
            ) // COMMENT For Method Security
            .httpBasic(Customizer.withDefaults()); // Basic auth

        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails jaya = User.builder()
        					   .username("jaya")
                               .password(passwordEncoder().encode("user"))
                               .roles("USER")
                               .build();

        UserDetails admin = User.builder()
        						.username("admin")
                                .password(passwordEncoder().encode("admin"))
                                .roles("ADMIN")
                                .build();

        return new InMemoryUserDetailsManager(jaya, admin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                               .password(passwordEncoder().encode("user"))
//                               .roles("USER")
//                               .build();
//
//        UserDetails admin = User.withUsername("admin")
//                                .password(passwordEncoder().encode("admin"))
//                                .roles("ADMIN")
//                                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }


}
