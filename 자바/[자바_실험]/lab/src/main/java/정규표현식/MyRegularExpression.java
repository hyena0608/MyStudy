package 정규표현식;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
public class MyRegularExpression {

    public static final Matcher matcherFromPreCompiledPattern =
            Pattern.compile("^[A-z]$").matcher("1234");

    public static final Pattern matcherFromPattern =
            Pattern.compile("^[A-z]$");
    private final List<? extends String> arguments = List.of("123", "124", "125");

    @Benchmark
    public void matcherFromPreCompiledPatternResetMatchesTest(Blackhole bh) {
        for (String argument : arguments) {
            bh.consume(MyRegularExpression.matcherFromPreCompiledPattern.reset(argument).matches());
        }
    }

}
