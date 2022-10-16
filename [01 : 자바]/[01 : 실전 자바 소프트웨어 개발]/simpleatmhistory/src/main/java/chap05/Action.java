package chap05;

/**
 * 예제 5-2
 * Action 인터페이스
 *
 * - 비즈니스 규칙 엔진과 구체적 액션의 결합을 제거한다.
 */
@FunctionalInterface
public interface Action {
    void execute();
}
