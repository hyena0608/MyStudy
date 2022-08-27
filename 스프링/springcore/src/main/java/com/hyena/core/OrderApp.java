package com.hyena.core;

import com.hyena.core.member.Grade;
import com.hyena.core.member.Member;
import com.hyena.core.member.MemberService;
import com.hyena.core.order.Order;
import com.hyena.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService =
                ctx.getBean("memberService", MemberService.class);
        OrderService orderService =
                ctx.getBean("orderService", OrderService.class);

        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
    }
}
