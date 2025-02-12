//package com.example.product_service.security;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/v1/categories/public/**").permitAll() // Public endpoints
//                .antMatchers("/api/v1/categories/admin/**").hasRole("ADMIN") // Admin endpoints
//                .antMatchers(HttpMethod.GET, "/api/v1/categories/**").hasAnyRole("ADMIN", "USER") // Accessible by both USER and ADMIN
//                .anyRequest().authenticated() // Other endpoints require authentication
//                .and()
//                .csrf().disable(); // Disable CSRF for simplicity, you can enable it if necessary
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}