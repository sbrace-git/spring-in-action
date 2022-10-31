package tacos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password("{bcrypt}$2a$10$KApEB4t5p994/4YbA6kPhOTrc1CBiugqP32QuBataT300UMyrK5xq")
                .roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password("{bcrypt}$2a$10$ZOqPFlZni1IhxVGDvDZ3VuvvAnQZW8NDOwP./rs5I/iqnOVh.PR6m")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    public static void main(String[] args) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String userPassword = encoder.encode("password");
        // main userPassword = {bcrypt}$2a$10$KApEB4t5p994/4YbA6kPhOTrc1CBiugqP32QuBataT300UMyrK5xq
        log.info("main userPassword = {}", userPassword);

        String adminPassword = encoder.encode("password");
        // main adminPassword = {bcrypt}$2a$10$ZOqPFlZni1IhxVGDvDZ3VuvvAnQZW8NDOwP./rs5I/iqnOVh.PR6m
        log.info("main adminPassword = {}", adminPassword);
    }
}
