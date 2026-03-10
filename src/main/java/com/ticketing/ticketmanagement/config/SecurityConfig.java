package com.ticketing.ticketmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails customer = User.withDefaultPasswordEncoder()
                .username("customer")
                .password("1234")
                .roles("CUSTOMER")
                .build();

        UserDetails agent = User.withDefaultPasswordEncoder()
                .username("agent")
                .password("1234")
                .roles("AGENT")
                .build();

        UserDetails supervisor = User.withDefaultPasswordEncoder()
                .username("supervisor")
                .password("1234")
                .roles("SUPERVISOR")
                .build();

        UserDetails caseManager = User.withDefaultPasswordEncoder()
                .username("case")
                .password("1234")
                .roles("CASE_MANAGER")
                .build();

        UserDetails qa = User.withDefaultPasswordEncoder()
                .username("qa")
                .password("1234")
                .roles("QA")
                .build();

        UserDetails analytics = User.withDefaultPasswordEncoder()
                .username("analytics")
                .password("1234")
                .roles("ANALYTICS")
                .build();

        return new InMemoryUserDetailsManager(
                customer, agent, supervisor, caseManager, qa, analytics
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/login", "/redirect").permitAll()

                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/agent/**").hasRole("AGENT")
                        .requestMatchers("/supervisor/**").hasRole("SUPERVISOR")
                        .requestMatchers("/case/**").hasRole("CASE_MANAGER")
                        .requestMatchers("/qa/**").hasRole("QA")
                        .requestMatchers("/analytics/**").hasRole("ANALYTICS")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/redirect", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                );

        return http.build();
    }
}
