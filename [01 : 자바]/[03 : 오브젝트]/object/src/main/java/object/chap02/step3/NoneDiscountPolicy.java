package object.chap02.step3;

import object.chap02.step01.DiscountPolicy;
import object.chap02.step01.Money;
import object.chap02.step01.Screening;

public class NoneDiscountPolicy extends DiscountPolicy {

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
