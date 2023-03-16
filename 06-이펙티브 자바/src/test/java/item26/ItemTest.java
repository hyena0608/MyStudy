package item26;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ItemTest {

    @Test
    void 타입_매개변수를_이용한다() {
        final List<String> values = new ArrayList<>();
    }

    @Test
    void Iterator_Raw_Type_타입_변환_시_예외가_발생한다() {
        final Collection stamp = new ArrayList();
        assertThatThrownBy(() -> convertFrom(stamp));
    }

    @Test
    void 컴파일러_자동_형변환() {
        List values = new ArrayList();
        values.add("1");
        values.add(2);

        String value0 = (String) values.get(0);
    }

    @Test
    void 모르는_타입의_원소도_받는_Raw_Type() {
        final Set s1 = new HashSet();
        final Set s2 = new HashSet();
        assertThatNoException().isThrownBy(() -> num(s1, s2));
    }

    int num(Set s1, Set s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1)) {
                result++;
            }
        }
        return result;
    }

    @Test
    void 컴파일러_타입_불안정_메시지() {
        final List values = new ArrayList();
        values.add("");
        values.add('a');
        values.add(1);
    }

    @Test
    void 매개변수화된_컬렉션_String_타입() {
        final List<String> values = new ArrayList<>();
        values.add("");
    }

    @Test
    void 매개변수화된_컬렉션_Object_타입() {
        final List<Object> values = new ArrayList<>();
        values.add("");
        values.add('a');
        values.add(1);
    }

    @Test
    void Raw_Type_대신에_제네릭_타입을_사용하라_V1() {
        final Set<String> s1 = new HashSet<>();
        final Set<String> s2 = new HashSet<>();
        assertThatNoException().isThrownBy(() -> numV1(s1, s2));
    }

    <T> int numV1(Set<T> s1, Set<T> s2) {
        int result = 0;
        for (T t : s1) {
            if (s2.contains(t)) {
                result++;
            }
        }
        return result;
    }

    @Test
    void Raw_Type_대신에_제네릭_타입을_사용하라_V2() {
        final Set<String> s1 = new HashSet<>();
        final Set<Character> s2 = new HashSet<>();
        assertThatNoException().isThrownBy(() -> numV2(s1, s2));
    }

    <T, E> int numV2(Set<T> s1, Set<E> s2) {
        int result = 0;
        for (T t : s1) {
            if (s2.contains(t)) {
                result++;
            }
        }
        return result;
    }

    @Test
    void Raw_Type_대신에_비한정적_와일드카드_타입을_사용하라_V3() {
        final Set<String> s1 = new HashSet<>();
        final Set<Character> s2 = new HashSet<>();
        s1.add("ss");
        s2.add('c');
        assertThatNoException().isThrownBy(() -> numV3(s1, s2));
    }

    int numV3(Set<?> s1, Set<?> s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1)) {
                result++;
            }
        }
        return result;
    }

    @Test
    void 비한정적_와일드카드_타입에는_원소를_넣으려_할_때_컴파일_에러가_발생한다() {
        final Set<?> s1 = new HashSet<>();
//         s1.add("ss");
//         s1.add('c');
//         s1.add(0);
    }




    static class Stamp<T> {
        Box<String> box = new Box<>();
        List values;
    }

    static class Box<T> {
        private T t;
    }

    private Stamp convertFrom(final Collection stamp) {
        return (Stamp) stamp.iterator().next();
    }
}
