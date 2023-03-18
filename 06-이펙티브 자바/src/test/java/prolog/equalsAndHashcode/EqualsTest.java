package prolog.equalsAndHashcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

class EqualsTest {

    @DisplayName("같은 값을 가져도 동일하지 않다고 판단된다.")
    @Test
    void equals_not_override() {
        final var 이름01 = new Name("이름");
        final var 이름02 = new Name("이름");

        assertThat(이름01).isNotEqualTo(이름02);
    }

    static class Name {
        private final String value;

        public Name(final String value) {
            this.value = value;
        }
    }

    @DisplayName("같은 값을 가지면 동일하다고 판단된다.")
    @Test
    void equals_override() {
        final var 이름01 = new Name_Override("이름");
        final var 이름02 = new Name_Override("이름");

        assertThat(이름01).isEqualTo(이름02);
    }

    static class Name_Override {
        private final String value;

        public Name_Override(final String value) {
            this.value = value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Name_Override that = (Name_Override) o;
            return Objects.equals(value, that.value);
        }
    }
}
