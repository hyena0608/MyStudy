package chap05;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * 예제 5-3
 * 비즈니스 규칙 엔진 기본 테스트현
 * 예제 5-5
 * 모킹으로 Action 객체 상호작용 검증
 * 예제 5-13
 * Facts를 이용하는 액션
 * 예제 5-16
 * 지역 변수 추론 var
 */
class BusinessRuleEngineTest {

//    @Test
//    void shouldHaveNoRulesInitially() {
//        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine();
//
//        assertEquals(0, businessRuleEngine.count());
//    }

//    @Test
//    void shouldAddTwoActions() {
//        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(facts);
//
//        businessRuleEngine.addAction(() -> {});
//        businessRuleEngine.addAction(() -> {});
//
//        assertEquals(2, businessRuleEngine.count());
//    }

//    @Test
//    void shouldExecuteOneAction() {
//        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine();
//        final Action mockAction = mock(Action.class);
//
//        businessRuleEngine.addAction(mockAction);
//        businessRuleEngine.run();
//
//        verify(mockAction).perform();
//    }

    @Test
    public void shouldPerformAnActionWithFacts() {
        final Action mockAction = mock(Action.class);
        final Facts mockFacts = mock(Facts.class);
        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(mockFacts);

        businessRuleEngine.addAction(facts -> {
            var jobTitle = facts.getFact("jobTitle");
            if ("CEO".equals(jobTitle)) {
                var name = facts.getFact("name");
                Mailer.sendEmail("aaa@aaa.com", "Relevant customer: " + name);
            }
        });
        businessRuleEngine.run();

        verify(mockAction).execute(mockFacts);
    }
}