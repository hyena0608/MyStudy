package 정규표현식;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;

class MyRegularExpressionTest {

    private final List<? extends String> arguments = List.of("123", "124", "125");

    MyRegularExpression myRegularExpression = new MyRegularExpression();

    @Test
    void matcherFromPreCompiledPatternResetMatches() {
        myRegularExpression.matcherFromPreCompiledPatternResetMatchesTest(new Blackhole("1"));
    }
}