package chap05;

/**
 * 예제 5-24
 * Report 클래스
 */
public class Report {

    private final ConditionalAction conditionalAction;
    private final Facts facts;
    private final boolean isPositive;

    public Report(Facts facts, ConditionalAction conditionalAction, boolean isPositive) {
        this.conditionalAction = conditionalAction;
        this.facts = facts;
        this.isPositive = isPositive;
    }

    public ConditionalAction getConditionalAction() {
        return conditionalAction;
    }

    public Facts getFacts() {
        return facts;
    }

    public boolean isPositive() {
        return isPositive;
    }

    @Override
    public String toString() {
        return "Report{" +
                "conditionalAction=" + conditionalAction +
                ", facts=" + facts +
                ", isPositive=" + isPositive +
                '}';
    }
}
