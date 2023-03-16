package toby;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Generics {

    @DisplayName("")
    @Test
    void 메서드명() {
        // given


        // when


        // then
    }

    private static <T extends Comparable<? super T>> T max(List<? extends T> lists) {
        return lists.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
    }
}
