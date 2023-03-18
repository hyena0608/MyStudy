package prolog.equalsAndHashcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.*;

public class EHTest {

    @DisplayName("서로 다른 key 값을 갖는다.")
    @Test
    void equals_override() {
        final var 이름01 = new Name_equals("HYENA");
        final var 이름02 = new Name_equals("HYENA");

        final Map<Name_equals, String> map = new HashMap<>();
        map.put(이름01, "값");
        map.put(이름02, "값");

        assertThat(map).containsExactly(
                entry(이름01, "값"),
                entry(이름02, "값")
        );
    }

    static class Name_equals {
        private final String value;

        public Name_equals(final String value) {
            this.value = value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Name_equals that = (Name_equals) o;
            return Objects.equals(value, that.value);
        }
    }

    @DisplayName("서로 다른 key 값을 갖는다.")
    @Test
    void hashcode_override() {
        final var 이름01 = new Name_hashcode("HYENA");
        final var 이름02 = new Name_hashcode("HYENA");

        System.out.println("이름01 = " + 이름01.hashCode());
        System.out.println("이름02 = " + 이름02.hashCode());
        final Map<Name_hashcode, String> map = new HashMap<>();
        map.put(이름01, "값");
        map.put(이름02, "값");

        assertThat(map).containsExactly(
                entry(이름01, "값"),
                entry(이름02, "값")
        );
    }

    static class Name_hashcode {
        private final String value;

        public Name_hashcode(final String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    @DisplayName("서로 같은 key 값을 갖는다.")
    @Test
    void equals_hashcode_override() {
        final var 이름01 = new Name_equals_hashcode("HYENA");
        final var 이름02 = new Name_equals_hashcode("HYENA");

        final Map<Name_equals_hashcode, String> map = new HashMap<>();
        map.put(이름01, "값");
        map.put(이름02, "값");

        assertAll(
                () -> assertThat(map).containsExactly(entry(이름01, "값")),
                () -> assertThat(map).containsExactly(entry(이름02, "값"))
        );
    }

    static class Name_equals_hashcode {
        private final String value;

        public Name_equals_hashcode(final String value) {
            this.value = value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Name_equals_hashcode that = (Name_equals_hashcode) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
