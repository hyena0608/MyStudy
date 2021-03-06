package com.hyena.serveltprogramming.servlets;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/calc")
public class CalculatorServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        int a = Integer.parseInt(req.getParameter("a"));
        int b = Integer.parseInt(req.getParameter("b"));

        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        writer.println("a = " + a + ", " + "b = " + b + "의 계산 결과 입니다.");
        writer.println("a + b = " + (a + b));
        writer.println("a - b = " + (a - b));
        writer.println("a * b = " + (a * b));
        writer.println("a / b = " + ((float) a / (float) b));
        writer.println("a % b = " + (a % b));

    }


}
