package com.samnart.load_balancer.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.samnart.load_balancer.filter.LoadBalancerFilter;

@Configuration
public class GatewayConfig {
    
    @Bean
    public GlobalFilter customGlobalFilter() {
        return new LoadBalancerFilter();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");

        // Use the REACTIVE version instead of servlet version
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
// package com.samnart.load_balancer.config;

// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsWebFilter;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import com.samnart.load_balancer.filter.LoadBalancerFilter;

// @Configuration
// public class GatewayConfig {
    
//     @Bean
//     public GlobalFilter customGlobalFilter() {
//         return new LoadBalancerFilter();
//     }

//     // @Bean
//     // @Primary
//     // public GlobalFilter loggingFilter() {
//     //     return new LoggingFilter();
//     // }

//     @Bean
//     public CorsWebFilter corsWebFilter() {
//         CorsConfiguration corsConfig = new CorsConfiguration();
//         corsConfig.setAllowCredentials(true);
//         corsConfig.addAllowedOriginPattern("*");
//         corsConfig.addAllowedHeader("*");
//         corsConfig.addAllowedMethod("*");

//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", corsConfig);

//         return new CorsWebFilter(source);
//     }
// }
