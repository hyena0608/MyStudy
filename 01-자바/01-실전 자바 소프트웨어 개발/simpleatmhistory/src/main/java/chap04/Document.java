package chap04;

import java.util.Map;

/**
 * 예제 4-3
 * -Document
 *  - 문서 관리 시스템에서만 만들 것
 *
 * - 클래스 속성에 String
 *  - 일반적인 형식
 *  - Importer의 종류와 관계없이 모든 속성이 일반적인 형식을 갖도록 구현
 *      - 보통 좋지 않은 방법으로 알려져 있다.
 *      - 문자열보다 강한 형식을 가진 속성이 훨씬 도움이 될 수 있다.
 *          - 문자열보다 더 쉽게 비교할 수 있기 때문이다.
 */
public class Document {

    private final Map<String, String> attributes;

    Document(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(final String attributeName) {
        return attributes.get(attributeName);
    }
}
