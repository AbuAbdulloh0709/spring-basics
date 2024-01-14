package com.epam.esm.security;

import com.epam.esm.exceptions.AccessDeniedHandlerEntryPoint;
import com.epam.esm.exceptions.AuthenticationHandlerEntryPoint;
import com.epam.esm.jwt.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JWTFilter jwtFilter;
    private final AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint;
    private final AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint;

    private static final String ADMIN = "ADMIN";

    @Autowired
    public SecurityConfig(JWTFilter jwtFilter, AuthenticationHandlerEntryPoint authenticationHandlerEntryPoint,
                          AccessDeniedHandlerEntryPoint accessDeniedHandlerEntryPoint) {
        this.jwtFilter = jwtFilter;
        this.authenticationHandlerEntryPoint = authenticationHandlerEntryPoint;
        this.accessDeniedHandlerEntryPoint = accessDeniedHandlerEntryPoint;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    req
                            //All
                            .requestMatchers("/login/**").permitAll()
                            .requestMatchers(GET, "/gift_certificates/**").permitAll()
                            .requestMatchers(POST, "/register", "/auth").permitAll()
                            //USER
                            .requestMatchers(POST, "/orders").fullyAuthenticated()
                            .requestMatchers(GET, "/tags/**", "/users/**", "/orders/**").fullyAuthenticated()
                            .anyRequest().hasRole(ADMIN);
                })
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandlerEntryPoint).authenticationEntryPoint(authenticationHandlerEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}
