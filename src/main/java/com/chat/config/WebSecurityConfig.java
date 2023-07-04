package com.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/", "/topics", "/faq", "/topic/*").permitAll() //Главная страница, топики, faq, просмотр топика отдельно
                .requestMatchers("/login", "/register").anonymous() //Аутентификация
                .requestMatchers("/messages").authenticated() //Сообщения
                .requestMatchers("/profile/**").authenticated() //Изменение профиля
                .requestMatchers("/topic/add").authenticated() //Создание топиков
                .requestMatchers("/topic/*/delete", "/topic/*/edit").hasAnyRole("ADMIN", "MODERATOR") //Удаление и изменение топиков
                .requestMatchers("/img/**", "/static/**").permitAll() //Статический контент
                .anyRequest().denyAll() //Остальные запросы
                //todo Сделать админов
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/?logout=success");

        return httpSecurity.build();
    }

}
