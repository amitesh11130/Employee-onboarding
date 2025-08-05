package com.springbootapi.security;

import com.springbootapi.response.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class ApiHttpSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private CustomAuthenticationEntryPoint entryPoint;

    private static final String[] PUBLIC_URL = {
            "/api/v1/employee/getAll",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"

    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) ->
                        auth
                                .requestMatchers(PUBLIC_URL).permitAll()
                                .requestMatchers("/api/v1/admin/save", "/api/v1/admin/saveAll", "/api/v1/admin/delete", "/api/v1/admin/updateAdmin/**")
                                .hasRole(Role.ADMIN.name())
                                .requestMatchers("/api/v1/admin/getAll", "/api/v1/admin/getById,/api/v1/employee/**")
                                .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                                .anyRequest().authenticated())

                .httpBasic(basic -> basic.authenticationEntryPoint(entryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(entryPoint))
                .build();
    }

}
