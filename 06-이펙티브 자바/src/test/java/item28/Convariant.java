package item28;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class Convariant {

    @Test
    void 배열은_공변이다() {
        final String string = new String();

        assertThat(string instanceof Object).isTrue();
    }

    @Test
    void 제네릭은_불공변이다() {
        final List<String> strings = new ArrayList<>();

        /**
         *Inconvertible types
         */
        // assertThat(strings instanceof List<Object>).isTrue();
    }
}
