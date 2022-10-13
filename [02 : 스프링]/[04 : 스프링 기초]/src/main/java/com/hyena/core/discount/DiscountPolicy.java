package com.hyena.core.discount;

import com.hyena.core.member.Member;

public interface DiscountPolicy {

    public int discount(Member member, int price);
}
