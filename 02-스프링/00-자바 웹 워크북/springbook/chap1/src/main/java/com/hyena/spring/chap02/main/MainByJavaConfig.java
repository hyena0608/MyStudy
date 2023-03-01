package com.hyena.spring.chap02.main;

import com.hyena.spring.chap02.AuthenticationService;
import com.hyena.spring.chap02.PasswordChangeService;
import com.hyena.spring.chap02.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainByJavaConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        AuthenticationService authService = ctx.getBean("authenticationService", AuthenticationService.class);
        authService.authenticate("hspark", "1234");

        PasswordChangeService pwChangeService = ctx.getBean("pwChangeService", PasswordChangeService.class);
        pwChangeService.changePassword("hspark", "1234", "5678");

        ctx.close();
    }
}
