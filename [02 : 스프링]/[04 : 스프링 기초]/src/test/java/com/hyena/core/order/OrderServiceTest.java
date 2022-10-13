package com.hyena.core.order;

import com.hyena.core.member.Grade;
import com.hyena.core.member.Member;
import com.hyena.core.member.MemberService;
import com.hyena.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();

    @Test
    void createOrder() {
        // given
        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
//        memberService.join(member);

        // when
//        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // then
//        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}