package item32;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class ArraysTest {

    @DisplayName("각 String 타입의 데이터 3개를 List로 묶어 반환한다.")
    @Test
    void asListV1() {
        // RawType인 values를 초기화한다.
        List<Object> values = asListV1("헤나1", "헤나2", "헤나3");

        //  단점 01. RawType List는 타입에 불안전하다.
        values.add('a');            // 힙 오염 (HeapPollution)
        values.add(1);              // 힙 오염 (HeapPollution)
        values.add(new Object());   // 힙 오염 (HeapPollution)

        // String value = (String) values.get(4);

        //  단점 02. asListV1 메서드의 인자로 String 타입의 데이터만 들어갈 수 있다.
        // asListV1(30, 20, 10);
        // asListV1('a', 'b', 'c');
        // asListV1(new Object(), new Object(), new Object());

        //  단점 03. asListV1 메서드의 매개변수 개수는 항상 3개이다.
        // asListV1("헤나1");
        // asListV1("헤나1", "헤나2");
        // asListV1("헤나1", "헤나2", "헤나3", "헤나4");
        // asListV1("헤나1", "헤나2", "헤나3", "헤나4", "헤나5");
        // asListV1("헤나1", "헤나2", "헤나3", "헤나4", "헤나5", "헤나6");

        // Raw Type인 List에 "헤나1", "헤나2", "헤나3"가 잘 들어있다.
        assertThat(values)
                .contains("헤나1")
                .contains("헤나2")
                .contains("헤나3");
    }

    private List<Object> asListV1(String s1, String s2, String s3) {
        // RawType List에 String 타입 데이터 s1, s2, s3를 추가한다.
        //  단점 01. RawType List는 타입에 불안전하다.
        //  단점 02. asListV1 메서드의 인자로 String 타입의 데이터만 들어갈 수 있다.
        //  단점 03. asListV1 메서드의 매개변수 개수는 항상 3개이다.
        List<Object> values = new ArrayList<>();
        values.add(s1);
        values.add(s2);
        values.add(s3);
        return values;
    }

    @DisplayName("제네릭 <T>를 이용해서 '단점 02'를 해결한다.")
    @Test
    void asListV2() {
        List<String> values = asListV2("헤나1", "헤나2", "헤나3");

        //  [해결] 단점 01. RawType List는 타입에 불안전하다.
        // values.add('a');             // => 컴파일 에러, 안전하다.
        // values.add(1);               // => 컴파일 에러, 안전하다.
        // values.add(new Object());    // => 컴파일 에러, 안전하다.

        //  [해결] 단점 02. asListV3 메서드의 인자로 String 타입의 데이터만 들어갈 수 있다.
        List<Integer> intValues = asListV2(1, 2, 3);                    // => 다른 타입의 값을 받을 수 있다.
        List<Character> charValues = asListV2('a', 'b', 'c');           // => 다른 타입의 값을 받을 수 있다.
        List<String> stringValues = asListV2("헤나1", "헤나2", "헤나3");    // => 다른 타입의 값을 받을 수 있다.

        //  단점 03. asListV3 메서드의 인자로 데이터가 3개만 들어갈 수 있다.
        // asListV3("헤나1", "헤나2", "헤나3", "헤나4");    // => 컴파일 에러, 매개변수 개수는 3개로 제한된다.

        assertThat(values)
                .contains("헤나1")
                .contains("헤나2")
                .contains("헤나3");
    }

    private <T> List<T> asListV2(T t1, T t2, T t3) {
        // 제네릭 타입 List<T>에 T 타입 인자 t1, t2, t3를 추가한다.
        List<T> values = new ArrayList<>();
        values.add(t1);
        values.add(t2);
        values.add(t3);
        return values;
    }

    @Test
    void asListV3() {
        List<String> values = asListV3(new String[]{"헤나1", "헤나2", "헤나3"});

        //  [해결] 단점 01. RawType List는 타입에 불안전하다.
        // values.add('a');             // => 컴파일 에러, 안전하다.
        // values.add(1);               // => 컴파일 에러, 안전하다.
        // values.add(new Object());    // => 컴파일 에러, 안전하다.

        // [해결] 단점 02. asListV3 메서드의 인자로 String 타입의 데이터만 들어갈 수 있다.
        List<Integer> intValues = asListV3(new Integer[]{1, 2, 3});                    // => 다른 타입의 값을 받을 수 있다.
        List<Character> charValues = asListV3(new Character[]{'a', 'b', 'c'});         // => 다른 타입의 값을 받을 수 있다.
        List<String> stringValues = asListV3(new String[]{"헤나1", "헤나2", "헤나3"});    // => 다른 타입의 값을 받을 수 있다.

        //  단점 03. 메서드 인자로 데이터가 3개만 들어갈 수 있다.

        asListV3(new String[]{"헤나1"});
        asListV3(new String[]{"헤나1", "헤나2"});
        asListV3(new String[]{"헤나1", "헤나2", "헤나3", "헤나4"});
        asListV3(new String[]{"헤나1", "헤나2", "헤나3", "헤나4", "헤나5"});
        asListV3(new String[]{"헤나1", "헤나2", "헤나3", "헤나4", "헤나5", "헤나6"});
    }

    private <T> List<T> asListV3(T[] ts) {
        List<T> values = new ArrayList<>();
        for (T t : ts) {
            values.add(t);
        }
        return values;
    }

    @Test
    void asListV4() {
        List<String> values = asListV4("헤나", "하이에나", "하히헤나");

        //  [해결] 단점 01. RawType List는 타입에 불안전하다.
        // values.add('a');             // => 컴파일 에러, 안전하다.
        // values.add(1);               // => 컴파일 에러, 안전하다.
        // values.add(new Object());    // => 컴파일 에러, 안전하다.

        //  [해결] 단점 02. 메서드의 인자로 String 타입의 데이터만 들어갈 수 있다.
        List<Integer> intValues = asListV4(1, 2, 3);                     // => 다른 타입의 값을 받을 수 있다.
        List<Character> charValues = asListV4('a', 'b', 'c');            // => 다른 타입의 값을 받을 수 있다.
        List<String> stringValues = asListV4("헤나1", "헤나2", "헤나3");    // => 다른 타입의 값을 받을 수 있다.

        //  [해결] 단점 03. 메서드 인자로 데이터가 3개만 들어갈 수 있다.
        asListV4("헤나1");
        asListV4("헤나1", "헤나2");
        asListV4("헤나1", "헤나2", "헤나3", "헤나4");
        asListV4("헤나1", "헤나2", "헤나3", "헤나4", "헤나5");
        asListV4("헤나1", "헤나2", "헤나3", "헤나4", "헤나5", "헤나6");
    }

    private <T> List<T> asListV4(T... ts) {
        List<T> values = new ArrayList<>();
        for (T t : ts) {
            values.add(t);
        }
        return values;
    }

//    @Test
//    void asListV5() {
//        List<String> values = asListV5("헤나01", "헤나02", "헤나03");
//
//        String name01 = values.get(0);
//        String name01Number = values.get(1); // ClassCastException 예외 발생
//    }
//
//    private <T> List<T> asListV5(T... ts) {
//        Object[] objects = ts;
//        objects[1] = List.of(1);
//
//
//        List<Object> values = new ArrayList<>();
//        int count = 0;
//        for (T t : ts) {
//            values.add(t);
//            values.add(count++); // 힙 오염 (Heap Pollution)
//        }
//        return (List<T>) values;
//    }

    @Test
    void asListV5() {
        List<String> values = asListV5(List.of("헤나01", "헤나02", "헤나03"), List.of());
    }

    private <T> List<T> asListV5(List<T>... ts) {
        Object[] objects = ts;
        List<Integer> counts = new ArrayList<>();
        for (int count = 1; count <= ts.length; count++) {
            counts.add(count);
        }
        objects[1] = counts; // 힙 오염 (Heap Pollution)

        String names = (String) ts[0].get(0);   // T는 String 타입일 것이다.
        String numbers = (String) ts[1].get(0); // T는 String 타입이 아니다. => ClassCastException 예외 발생

        List<Object> values = new ArrayList<>();
        for (Object o : objects) {
            values.add(o);
        }
        return (List<T>) values;
    }

    @Test
    void asListV6() {
        String[] values = asListV6("헤나01", "1");
    }

    private <T> T[] asListV6(T a, T b) {
        return convertBy(a, b); // ClassCaseException 예외 발생 => Object[] 을 String[]으로 변환할 수 없다.
    }

    private <T> T[] convertBy(T... ts) {
        return ts;
    }

    @Test
    void asListV7() {
        // 가변인수 배열이 다른 객체를 참조하지 않는다.
        // 가변인수 배열 자신을 반환하지 않는다.
        // => 타입 안전한 메서드 asListV7
        List<List<String>> nameAndNumbers = asListV7(List.of("헤나01", "헤나02", "헤나03"));
        List<String> numbers = new ArrayList<>();
        for (int count = 1; count <= nameAndNumbers.get(0).size(); count++) {
            numbers.add(String.valueOf(count));
        }
        nameAndNumbers.add(numbers);

        for (List<String> values : nameAndNumbers) {
            for (String value : values) {
                System.out.println(value);
            }
        }
    }

    private <T> List<T> asListV7(T... ts) {
        List<T> values = new ArrayList<>();
        for (T t : ts) {
            values.add(t); // 가변인수 배열이 다른 객체를 참조하지 않는다.
        }
        return values; // 가변인수 배열 자신을 반환하지 않는다.
    }
}
