package item32;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.*;

public class TypeDanger {

    /**
     * 32-1
     */
    @Test
    void 제네릭과_varargs를_혼용하면_타입_안정성이_깨진다() {
        assertThatThrownBy(() -> dangerous(List.of(), List.of()))
                .isInstanceOf(ClassCastException.class);
    }

    void dangerous(List<String>... stringLists) {
        List<Integer> intList = List.of(42);
        Object[] objects = stringLists;
        objects[0] = intList; // 힙 오염 발생
        String s = stringLists[0].get(0); // ClassCastException
    }

    /**
     * 32-2
     * Note: /Users/hyunseo/Desktop/untitled/src/test/java/item32/TypeDanger.java uses or overrides a deprecated API.
     * Note: Recompile with -Xlint:deprecation for details.
     * Note: /Users/hyunseo/Desktop/untitled/src/test/java/item32/TypeDanger.java uses unchecked or unsafe operations.
     * Note: Recompile with -Xlint:unchecked for details.
     */
    @Test
    void 자신의_제네릭_매개변수_배열의_참조_노출은_안전하지_않다() {
        toArray(new String(), new Character('c'), new Integer(10));
    }

    static <T> T[] toArray(T... args) {
        return args;
    }



    @Test
    void pickTwo_메서드는_ClassCastException이_발생한다() {
        assertThatThrownBy(() -> {
            String[] values = pickTwo("안", "녕", "하세요");
        }).isInstanceOf(ClassCastException.class);
    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0: return toArray(a, b);
            case 1: return toArray(a, c);
            case 2: return toArray(b, c);
        }
        throw new AssertionError();
    }

    /**
     * 32-3
     */
    @Test
    void 제네릭_varargs_매개변수를_안전하게_사용하는_메서드() {
        assertThatNoException().isThrownBy(() -> {
            List<String> flatten = flattenV1(List.of("안", "녕", "하세요"));
        });
    }

    /**
     * 32-4
     */
    @Test
    void 제네릭_varargs_매개변수를_List로_대체한_예_타입_안전하다() {
        List<String> hello = List.of("하이");
        List<String> bye = List.of("바이");
        assertThatNoException().isThrownBy(() -> {
            List<String> flatten = flattenV2(List.of(hello, bye));
        });
    }

    @SafeVarargs
    static <T> List<T> flattenV1(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    static <T> List<T> flattenV2(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    @Test
    void pickTwo_메서드의_toArray_메서드를_List_버전으로_바꾸면_타입_안전하다() {
        assertThatNoException().isThrownBy(() -> pickTwoV2("안", "녕", "하세요"));
    }

    static <T> List<T> pickTwoV2(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0: return List.of(a, b);
            case 1: return List.of(a, b);
            case 2: return List.of(a, b);
        }
        throw new AssertionError();
    }

    @Test
    void generic_array() {
//        List<String>[] groups = new ArrayList<String>[2];
    }

    @Test
    void generic_varargs_array() {
        generic_varargs_array(List.of("헤나", "하이에나"));
    }

    <T> void generic_varargs_array(List<T>... args) {
    }

    @DisplayName("첫 번째 그룹에 속한 그룹원들의 이름을 번호로 바꿀 때 ClassCastException 예외가 발생한다.")
    @Test
    void 매개변수화_타입과_가변인수() {
        assertThatThrownBy(() -> changeGroupNameToNumber(List.of("헤나", "하이에나"), List.of("누렁이", "백구")))
                .isInstanceOf(ClassCastException.class);
    }

    static void changeGroupNameToNumber(List<String>... groups) {
        List<Integer> firstGroupNumbers = List.of(1, 2);
        Object[] objects = groups;
        objects[0] = firstGroupNumbers;
        String firstPersonNumber = groups[0].get(0);
    }

    @DisplayName("첫 번째 그룹에 속한 그룹원들의 이름을 번호로 바꿀 때 ClassCastException 예외가 발생한다.")
    @Test
    void 정규_타입_매개변수와_가변인수() {
        changePosition("헤나", "하이에나");
    }

    static <T> T[] toMembers(T... members) {
        return members;
    }

    static <T> T[] changePosition(T... groupMember) {
        T tempMember = groupMember[0];
        T firstMember = groupMember[1];
        T secondMember = tempMember;
        return toMembers(firstMember, secondMember);
    }
}
