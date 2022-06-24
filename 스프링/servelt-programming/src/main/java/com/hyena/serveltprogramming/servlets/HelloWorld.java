package com.hyena.serveltprogramming.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


public class HelloWorld implements Servlet {
    ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init() 호출됨");
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println("getServletConfig() 호출됨");
        return this.config;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("service() 호출됨");
    }

    @Override
    public String getServletInfo() {
        System.out.println("getServletInfo() 호출됨");
        return "version=1.0;author=parkhyunseo;copyright=parkhyunseo 2022";
    }

    @Override
    public void destroy() {
        System.out.println("destroy() 호출됨");
    }
}
