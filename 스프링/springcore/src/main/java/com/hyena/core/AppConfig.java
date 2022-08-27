package com.hyena.core;

import com.hyena.core.discount.DiscountPolicy;
import com.hyena.core.discount.RateDiscountPolicy;
import com.hyena.core.member.MemberRepository;
import com.hyena.core.member.MemberService;
import com.hyena.core.member.MemberServiceImpl;
import com.hyena.core.member.MemoryMemberRepository;
import com.hyena.core.order.OrderService;
import com.hyena.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
