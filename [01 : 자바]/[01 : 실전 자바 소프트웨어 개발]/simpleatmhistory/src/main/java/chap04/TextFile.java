package chap04;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static chap04.Attributes.PATH;

/**
 * 예제 4-12
 * - TextFile 정의
 * - 텍스트 파일이라는 기본 개념을 모델링하는 클래스
 * - 도메인 클래스를 이용하여 유연성을 개선
 * - 도메인 클래스를 이용하여 쉽게 깨지지 않는 계층을 만들지 않고 코드 재사용이 가능하도록 함
 */
public class TextFile {

    private final Map<String, String> attributes;
    private final List<String> lines;

    TextFile(final File file) throws IOException {
        attributes = new HashMap<>();
        attributes.put(PATH, file.getPath());
        lines = Files.lines(file.toPath()).collect(Collectors.toList());
    }

    Map<String, String> getAttributes() {
        return attributes;
    }

    int addLines(
            final int start,
            final Predicate<String> isEnd,
            final String attributeName) {

        final StringBuilder accumulator = new StringBuilder();
        int lineNumber;
        for (lineNumber = start; lineNumber < lines.size(); lineNumber++) {
            final String line = lines.get(lineNumber);
            if (isEnd.test(line)) {
                break;
            }

            accumulator.append(line);
            accumulator.append("\n");
        }

        attributes.put(attributeName, accumulator.toString().trim());
        return lineNumber;
    }

    void addLineSuffix(final String prefix, final String attributeName) {
        for (final String line : lines) {
            if (line.startsWith(prefix)) {
                attributes.put(attributeName, line.substring(prefix.length()));
                break;
            }
        }
    }

}
