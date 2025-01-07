package com.chat.app.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // Keep or enable CSRF based on app requirements
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagementConfigure ->
                        sessionManagementConfigure.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .formLogin(form -> form
                        .loginPage("/auth/login") // Specify login endpoint
                        .loginProcessingUrl("/auth/login") // Processing endpoint for form login
                        .defaultSuccessUrl("/chat/home", true) // Redirect after successful login
                        .failureUrl("/auth/login?error=true") // Redirect on login failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // Define logout URL
                        .logoutSuccessUrl("/auth/login?logout=true") // Redirect after logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .build();
    }
}
