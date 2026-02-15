package com.example.order.management.config;

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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/orders/security").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/orders/*").hasAuthority("GET_ORDER")
                        .requestMatchers(HttpMethod.GET, "/orders").hasAuthority("CREATE_ORDER")
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasAuthority("CREATE_ORDER")
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasAuthority("DELETE_ORDER")

                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService users(){
        UserDetails dev = User.withDefaultPasswordEncoder()
                .username("dev")
                .password("password")
                .roles("DEV")
                .authorities("GET_ORDER")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .authorities("GET_ORDER", "CREATE_ORDER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .authorities("GET_ORDER", "CREATE_ORDER", "CANCEL_ORDER")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(dev, user, admin);
    }
}

