package chap05;

/**
 * 예제 5-26
 * Condition 인터페이스
 */
@FunctionalInterface
public interface Condition {
    boolean evaluate(Facts facts);
}
