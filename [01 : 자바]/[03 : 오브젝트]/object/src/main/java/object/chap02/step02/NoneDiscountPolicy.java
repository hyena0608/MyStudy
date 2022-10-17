package object.chap02.step02;

/**
 * Movie와 DiscountPolicy는 수정하지 않고
 * NoneDiscountPolicy라는 새로운 클래스를 추가하는 것만으로
 * 애플리케이션의 기능을 확장했다.
 */
public class NoneDiscountPolicy extends DiscountPolicy {

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
