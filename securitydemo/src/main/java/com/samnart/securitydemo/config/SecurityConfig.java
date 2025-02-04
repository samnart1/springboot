package com.samnart.securitydemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.samnart.securitydemo.jwt.AuthEntryPointJwt;
import com.samnart.securitydemo.jwt.AuthTokenFilter;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/signin").permitAll()
                .anyRequest().authenticated()
            )

            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
        
        
        
        
        // http.authorizeHttpRequests((requests) -> requests.requestMatchers("/h2-console/**").permitAll())
        // .build();
    }
}
