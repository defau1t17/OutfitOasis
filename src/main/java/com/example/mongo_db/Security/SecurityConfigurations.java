package com.example.mongo_db.Security;

import com.example.mongo_db.Entity.Client.Client;
import com.example.mongo_db.Service.Clients.ClientAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private ClientAuthentication clientAuthentication;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(requests -> {
                            try {
                                requests
                                        .requestMatchers("/shop/client/account/**").hasAuthority("ROLE_CLIENT")
                                        .requestMatchers("/shop/administration/**").hasAuthority("ROLE_ADMIN")
                                        .requestMatchers("/shop/producer/request/form").hasAuthority("ROLE_CLIENT")
                                        .requestMatchers("/shop/catalog**", "/shop/client/registration", "/shop/client/login", "/shop/help").permitAll()
                                        .requestMatchers("/images/**", "/**").permitAll()
                                        .anyRequest().authenticated()
                                        .and()
                                        .exceptionHandling(handle -> handle.accessDeniedHandler((request, response, authException) -> {
                                            Client global_client = (Client) request.getSession().getAttribute("global_client");
                                            if (global_client == null) {
                                                response.sendRedirect("/shop/client/login");
                                            } else {
                                                response.sendRedirect(clientAuthentication.redirectAuthenticatedClientByRole(global_client));
                                            }
                                        }))
                                        .exceptionHandling(handle -> handle.authenticationEntryPoint((request, response, authException) -> {
                                            Client global_client = (Client) request.getSession().getAttribute("global_client");
                                            if (global_client == null) {
                                                response.sendRedirect("/shop/client/login");
                                            } else {
                                                response.sendRedirect(clientAuthentication.redirectAuthenticatedClientByRole(global_client));
                                            }
                                        }));
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .httpBasic();
        return httpSecurity.build();

    }

}

