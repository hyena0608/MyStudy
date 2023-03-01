package chap05;

import java.util.ArrayList;
import java.util.List;

/**
 * 예제 5-1
 * 비즈니스 규칙 엔진의 기본 API
 * 예제 5-4
 * 비즈니스 규칙 엔진 기본 구현
 * 예제 5-6
 * run() 메서드 구현
 * 예제 5-12
 * Facts 를 이용한 BusinessRuleEngine
 */
public class BusinessRuleEngine {

    private final List<Action> actions;
    private final Facts facts;

    public BusinessRuleEngine(Facts facts) {
        this.facts = facts;
        this.actions = new ArrayList<>();
    }

    public void addAction(final Action action) {
        this.actions.add(action);
    }

    public int count() {
        return this.actions.size();
    }

    public void run() {
        this.actions.forEach(action -> action.execute(facts));
    }
}
