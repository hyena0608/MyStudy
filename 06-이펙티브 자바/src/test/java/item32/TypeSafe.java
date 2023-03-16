package item32;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TypeSafe {

    @DisplayName("첫 번째 그룹에 속한 그룹원들의 이름만을 가져온다.")
    @Test
    void 타입_안전한_메서드() {
        assertThatNoException()
                .isThrownBy(() -> {
                    List<String> firstGroupNames = getFirstGroupNames(List.of("헤나", "하이에나"), List.of("누렁이", "백구"));
                    String name0 = firstGroupNames.get(0);
                    String name1 = firstGroupNames.get(1);
                });


         // List<String> firstGroupNames = getFirstGroupNames(List.of(1, 2), List.of("d", 4)); // ERROR, 컴파일 에러
    }

    @SafeVarargs
    static <T> List<T> getFirstGroupNames(List<T>... groups) {
        List<T> names = new ArrayList<>();
        if (groups.length != 0 && !groups[0].isEmpty()) {
            names.addAll(groups[0]);
        }
        return names;
    }


    @DisplayName("첫 번째 그룹에 속한 그룹원들의 이름만을 가져온다.")
    @Test
    void 타입_안전한_메서드_V2() {
        assertThatNoException()
                .isThrownBy(() -> {
                    List<String> firstGroupNames = getFirstGroupNamesV2(List.of(List.of("헤나", "하이에나"), List.of("누렁이", "백구")));
                    String name0 = firstGroupNames.get(0);
                    String name1 = firstGroupNames.get(1);
                });

        // List<String> firstGroupNames = getFirstGroupNamesV2(List.of(List.of(1, 2), List.of("d", 4))); // ERROR, 컴파일 에러
    }

    static <T> List<T> getFirstGroupNamesV2(List<List<T>> groups) {
        List<T> names = new ArrayList<>();
        if (!groups.isEmpty() && !groups.get(0).isEmpty()) {
            names.addAll(groups.get(0));
        }
        return names;
    }
}
