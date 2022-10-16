package chap05;

/**
 * 예제 5-27
 * 규칙의 개념 모델링
 */
@FunctionalInterface
interface Rule {
    void perform(Facts facts);
}

class DefaultRule implements Rule {

    private final Condition condition;
    private final Action action;

    public DefaultRule(Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public void perform(final Facts facts) {
        if (condition.evaluate(facts)) {
            action.execute(facts);
        }
    }
}