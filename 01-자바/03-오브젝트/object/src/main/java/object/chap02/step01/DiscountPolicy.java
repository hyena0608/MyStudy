package object.chap02.step01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 부모 클래스 : DiscountPolicy
 * 자식 클래스 : AmountDiscountPolicy, PercentDiscountPolicy
 * - 두 클래스는 대부분의 코드가 유사하다.
 * - DiscountPolicy의 인스턴스를 생성할 필요가 없기에 추상 클래스로 구현한다.
 * <p>
 * Template Method 패턴 적용
 */
public abstract class DiscountPolicy {

    /**
     * 할인 정책은 여러 개의 할인 조건을 포함할 수 있다.
     */
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    /**
     *  [Template Method] 기본적인 알고리즘의 흐름 구현
     */
    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }

        return Money.ZERO;
    }

    /**
     * 실제로 계산을 하는 부분은 추상메서드 getDiscountAmount가 위임받는다.
     * [Template Method] 중간에 자식 클래스가 구현한다.
     */
    abstract protected Money getDiscountAmount(Screening screening);
}
