package item32;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class File {

    @Test
    void asListV1() {
        List values = asListV1("헤나1", "헤나2", "헤나3");

        values.add('a');            // 힙 오염 (HeapPollution)
        values.add(1);              // 힙 오염 (HeapPollution)
        values.add(new Object());   // 힙 오염 (HeapPollution)

        String value = (String) values.get(3);  // ClassCastException 예외 발생
    }

    private List asListV1(String s1, String s2, String s3) {
        //  단점 01. RawType List는 타입에 불안전하다.
        List values = new ArrayList();
        values.add(s1);
        values.add(s2);
        values.add(s3);
        return values;
    }

    @Test
    void asListV2() {
        List<String> values = asListV2("헤나1", "헤나2", "헤나3");

        //  [해결] 단점 01. RawType List는 타입에 불안전하다.
        // values.add('a');             // => 컴파일 에러, 안전하다.
        // values.add(1);               // => 컴파일 에러, 안전하다.
        // values.add(new Object());    // => 컴파일 에러, 안전하다.

        //  [해결] 단점 02. 메서드의 인자로 String 타입의 데이터만 들어갈 수 있다.
        List<Integer> intValues = asListV2(1, 2, 3);                     // => 다른 타입의 값을 받을 수 있다.
        List<Character> charValues = asListV2('a', 'b', 'c');            // => 다른 타입의 값을 받을 수 있다.
        List<String> stringValues = asListV2("헤나1", "헤나2", "헤나3");    // => 다른 타입의 값을 받을 수 있다.

        //  단점 03. 메서드의 매개변수 개수는 항상 3개이다.
        // asListV2("헤나1");
        // asListV2("헤나1", "헤나2");
        // asListV2("헤나1", "헤나2", "헤나3", "헤나4");
        // asListV2("헤나1", "헤나2", "헤나3", "헤나4", "헤나5");
        // asListV2("헤나1", "헤나2", "헤나3", "헤나4", "헤나5", "헤나6");
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

        // [해결] 단점 02. 메서드 인자로 String 타입의 데이터만 들어갈 수 있다.
        List<Integer> intValues = asListV3(new Integer[]{1, 2, 3});                    // => 다른 타입의 값을 받을 수 있다.
        List<Character> charValues = asListV3(new Character[]{'a', 'b', 'c'});         // => 다른 타입의 값을 받을 수 있다.
        List<String> stringValues = asListV3(new String[]{"헤나1", "헤나2", "헤나3"});    // => 다른 타입의 값을 받을 수 있다.

        // [해결] 단점 03. 메서드 인자로 데이터가 3개만 들어갈 수 있다.

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
}
