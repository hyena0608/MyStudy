package object.chap02.step01;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}
