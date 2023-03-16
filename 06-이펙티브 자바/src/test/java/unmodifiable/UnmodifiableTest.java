package unmodifiable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UnmodifiableTest {

    @Test
    void unmodifiable_리스트는_정렬_할_수_없다() {
        final List<Integer> unmodifiableValues = Collections.unmodifiableList(new ArrayList<>());

        assertThatThrownBy(() -> Collections.sort(unmodifiableValues))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void unmodifiable_리스트는_데이터를_추가_할_수_없다() {
        final List<Integer> unmodifiableValues = Collections.unmodifiableList(new ArrayList<>());

        assertThatThrownBy(() -> unmodifiableValues.add(10))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void unmodifiable_리스트는_데이터를_삭제_할_수_없다() {
        final List<Integer> unmodifiableValues = Collections.unmodifiableList(new ArrayList<>());

        assertThatThrownBy(() -> unmodifiableValues.remove(10))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void unmodifiable_리스트는_내부_데이터를_변경_할_수_있다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));

        final List<Name> unmodifiableNames = Collections.unmodifiableList(names);
        unmodifiableNames.get(0).setValue("하이에나");
        final Name name = unmodifiableNames.get(0);

        assertThat(name).hasFieldOrPropertyWithValue("value", "하이에나");

    }

    @Test
    void unmodifiable_리스트는_방어적_복사를_하지_않는다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));
        final List<Name> newNames = Collections.unmodifiableList(names);
        newNames.remove(0);

        assertThat(names).hasSize(0);
    }

    @Test
    void unmodifiable_리스트는_깊은_방어적_복사를_하지_않는다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));
        final List<Name> newNames = names.stream().collect(Collectors.toList());
        newNames.get(0).setValue("하이에나");

        Name name = names.get(0);

        assertThat(name.getValue()).isEqualTo("하이에나");
    }

    private static class Name {
        private String value;

        public Name(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }
}

