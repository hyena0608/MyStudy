package chap05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 예제 5-23
 * 조건 인스펙터
 */
public class Inspector {

    private final List<ConditionalAction> conditionalActionList;

    public Inspector(final ConditionalAction...conditionalActions) {
        this.conditionalActionList = Arrays.asList(conditionalActions);
    }

    public List<Report> inspect(final Facts facts) {
        final List<Report> reportList = new ArrayList<>();
        for (ConditionalAction conditionalAction : conditionalActionList) {
            final boolean conditionResult = conditionalAction.evaluate(facts);
            reportList.add(new Report(facts, conditionalAction, conditionResult));
        }
        return reportList;
    }
}
