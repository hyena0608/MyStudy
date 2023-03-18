package prolog.equalsAndHashcode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashCodeTest {

    @DisplayName("보통 객체 마다 다른 값을 반환한다.")
    @Test
    void hashCode_not_override() {
        final var 이름01 = new Name("이름");
        final var 이름02 = new Name("이름");

        final var 이름01_해쉬코드 = System.identityHashCode(이름01);
        final var 이름02_해쉬코드 = System.identityHashCode(이름02);

        System.out.println("이름01_해쉬코드 = " + 이름01_해쉬코드);
        System.out.println("이름02_해쉬코드 = " + 이름02_해쉬코드);

        assertThat(이름01_해쉬코드).isNotEqualTo(이름02_해쉬코드);
    }

    static class Name {
        private final String value;

        public Name(final String value) {
            this.value = value;
        }
    }

    @DisplayName("같은 값일 경우 같은 hashcode를 반환되게 한다.")
    @Test
    void hashCode_override() {
        final var 이름01 = new Name_Override("이름");
        final var 이름02 = new Name_Override("이름");

        final var 이름01_해쉬코드 = 이름01.hashCode();
        final var 이름02_해쉬코드 = 이름02.hashCode();

        System.out.println("이름01 = " + 이름01_해쉬코드);
        System.out.println("이름02 = " + 이름02_해쉬코드);

        assertThat(이름01_해쉬코드).isEqualTo(이름02_해쉬코드);
    }

    static class Name_Override {
        private final String value;

        public Name_Override(final String value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }
}
