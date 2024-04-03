package ru.gb.springbootlesson3.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        return httpSecurity
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("ui/**").hasAnyAuthority("admin")
                        .requestMatchers("admin/**").hasAuthority("admin")
                        .requestMatchers("ui/readers/**").hasAuthority("user")
                        .requestMatchers("ui/books/**").hasAnyAuthority("user","admin")
                        .requestMatchers("auth/**").authenticated()
                        .requestMatchers("any/**").permitAll()
                        .anyRequest().denyAll()
                )
                .formLogin(Customizer.withDefaults())

                .build();
    }
}