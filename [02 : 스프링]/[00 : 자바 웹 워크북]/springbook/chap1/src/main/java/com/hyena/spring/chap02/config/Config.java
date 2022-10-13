package com.hyena.spring.chap02.config;

import com.hyena.spring.chap02.AuthFailLogger;
import com.hyena.spring.chap02.AuthenticationService;
import com.hyena.spring.chap02.User;
import com.hyena.spring.chap02.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Config {

    @Bean
    public User user1() {
        return new User("hspark", "1234");
    }

    @Bean(name = "user")
    public User user() {
        return new User("hyena", "qwer");
    }

    @Bean
    public UserRepository userRepository() {
        UserRepository userRepository = new UserRepository();
        userRepository.setUsers(Arrays.asList(user1(), user()));
        return userRepository;
    }

    @Bean
    public AuthFailLogger authFailLogger() {
        AuthFailLogger logger = new AuthFailLogger();
        logger.setThreshold(2);
        return logger;
    }

    @Bean
    public AuthenticationService authenticationService() {
        AuthenticationService authService = new AuthenticationService();
        authService.setFailLogger(authFailLogger());
        authService.setUserRepository(userRepository());
        return authService;
    }
}
