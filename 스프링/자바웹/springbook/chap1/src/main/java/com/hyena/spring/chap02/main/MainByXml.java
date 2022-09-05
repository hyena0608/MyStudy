package com.hyena.spring.chap02.main;


import com.hyena.spring.chap02.AuthException;
import com.hyena.spring.chap02.AuthenticationService;
import com.hyena.spring.chap02.PasswordChangeService;
import com.hyena.spring.chap02.UserNotFoundException;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainByXml {

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:chap02_config.xml");
        AuthenticationService authService = ctx.getBean("authenticationService", AuthenticationService.class);

        runAuthAndCatchAuthEx(authService, "hspark", "1234");
        runAuthAndCatchAuthEx(authService, "hspark", "1233");
        runAuthAndCatchAuthEx(authService, "hspark", "1244");

        try {
            authService.authenticate("hspark2", "1234");
        } catch (UserNotFoundException ex) {}
        authService.authenticate("hspark", "1234");
        PasswordChangeService pwChgService = ctx.getBean(PasswordChangeService.class);
        pwChgService.changePassword("hspark", "1234", "5678");
        runAuthAndCatchAuthEx(authService, "hspark", "1234");
        authService.authenticate("hspark", "5678");
        ctx.close();
    }

    private static void runAuthAndCatchAuthEx(
            AuthenticationService authService, String userId, String password) {
        try {
            authService.authenticate(userId, password);
        } catch (AuthException ex) {}
    }
}
