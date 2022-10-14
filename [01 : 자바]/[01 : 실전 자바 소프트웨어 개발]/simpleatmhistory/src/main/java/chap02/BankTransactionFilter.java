package chap02;

/**
 * 예제 3-4
 * BankTransactionFilter 인터페이스
 *
 * OCP 원칙 적용
 * - OCP를 이용하면 코드를 직접 변경하지 않고 메서드나 클래스의 동작을 바꿀 수 있다.
 * - 여기서는 예제 3-4에 있었던 비즈니스 로직을 담당하는 BankTransactionFilter 인터페이스를 만들어 문제를 해결한다.
 *
 * `@FunctionalInterface`
 * - 함수형 인터페이스
 * - 인터페이스의 의도를 더 명확하게 표현할 수 있다.
 */
@FunctionalInterface
public interface BankTransactionFilter {
    boolean test(BankTransaction bankTransaction);
}
