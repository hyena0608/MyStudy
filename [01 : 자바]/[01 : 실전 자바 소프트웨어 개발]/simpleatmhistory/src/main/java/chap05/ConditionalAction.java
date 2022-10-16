package chap05;

/**
 * 예제 5-22
 * ConditionalAction 인터페이스
 */
public interface ConditionalAction {
    boolean evaluate(Facts facts);
    void perform(Facts facts);
}
