package com.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/login", "/register").anonymous()
                .requestMatchers("/messages").authenticated()
                .requestMatchers(HttpMethod.GET, "/topic/add", "/topic/*/edit").authenticated()
                .requestMatchers(HttpMethod.POST, "/topic/add", "/topic/*/", "/topic/*/delete").authenticated()
                .requestMatchers(HttpMethod.GET, "/", "/topics", "/faq", "/topic/*").permitAll()
                .requestMatchers("/img/**", "/static/**").permitAll()
                //todo Разрешить только админам
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=success");

        return httpSecurity.build();
    }

}
