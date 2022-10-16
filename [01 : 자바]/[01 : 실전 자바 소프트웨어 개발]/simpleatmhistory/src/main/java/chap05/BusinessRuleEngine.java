package chap05;

import java.util.ArrayList;
import java.util.List;

/**
 * 예제 5-1
 * 비즈니스 규칙 엔진의 기본 API
 * 예제 5-4
 * 비즈니스 규칙 엔진 기본 구현
 */
public class BusinessRuleEngine {

    private final List<Action> actions;

    public BusinessRuleEngine() {
        this.actions = new ArrayList<>();
    }

    public void addAction(final Action action) {
        this.actions.add(action);
    }

    public int count() {
        return this.actions.size();
    }

    public void run() {
        throw new UnsupportedOperationException();
    }
}
