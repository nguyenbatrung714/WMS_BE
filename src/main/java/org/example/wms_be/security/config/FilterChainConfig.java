package org.example.wms_be.security.config;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.security.jwt.AuthEntryPointJwt;
import org.example.wms_be.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class FilterChainConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(registry ->
                        registry
//                                .requestMatchers("api/v1/categories/**").authenticated()
//                                .requestMatchers("api/v1/consignment/**").authenticated()
//                                .requestMatchers("api/v1/customers/**").authenticated()
//                                .requestMatchers("api/v1/products/**").authenticated()
//                                .requestMatchers("api/v1/suppliers/**").authenticated()
//                                .requestMatchers("api/v1/warehouses/**").authenticated()
//                                .requestMatchers("api/v1/zones/**").authenticated()
//                                .requestMatchers("api/v1/zone-details/**").authenticated()
//                                .requestMatchers("api/v1/users/**").authenticated()
//                                .requestMatchers("api/v1/inventories/**").authenticated()
//
//                                // account
//                                .requestMatchers("/api/v1/auth/**").permitAll()
//                                .requestMatchers("/api/v1/forgot-password/**").permitAll()
//
//                                // ib
//                                .requestMatchers(HttpMethod.GET, "api/v1/inbounds").authenticated()
//                                .requestMatchers(HttpMethod.POST, "api/v1/inbounds").hasRole("Manager")
//                                .requestMatchers("api/v1/purchase-details/**").hasRole("User")
//                                .requestMatchers("api/v1/purchase-requests/**").hasRole("User")
//                                .requestMatchers("api/v1/purchase-orders/**").hasRole("Manager")
//
//                                // ob
//                                .requestMatchers("api/v1/purchase-details-outbound/**").hasRole("User")
//                                .requestMatchers("api/v1/purchase-request-ob/**").hasRole("User")
//
//                                // Barcode
//                                .requestMatchers("api/v1/barcodes/**").permitAll()
                                .requestMatchers("/v3/api-docs/**",
                                        "/swagger-ui/**").permitAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authEntryPointJwt)
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
