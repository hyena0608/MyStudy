package unmodifiable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CollectorToUnmodifiableListTest {

    @Test
    void collectorsToUnmodifiable_정렬_할_수_없다() {
        final List<Integer> values = new ArrayList<>(List.of(1, 3, 2));
        final List<Integer> newValues = values.stream().collect(Collectors.toUnmodifiableList());

        assertThatThrownBy(() -> Collections.sort(newValues))
                        .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void collectorsToUnmodifiable_데이터를_추가_할_수_없다() {
        final List<Integer> values = new ArrayList<>();
        final List<Integer> newValues = values.stream().collect(Collectors.toUnmodifiableList());

        assertThatThrownBy(() -> newValues.add(1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void collectorsToUnmodifiable_데이터를_삭제_할_수_없다() {
        final List<Integer> values = (new ArrayList<>());
        final List<Integer> newValues = values.stream().collect(Collectors.toUnmodifiableList());

        assertThatThrownBy(() -> newValues.remove(1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void collectorsToUnmodifiable_내부_데이터를_변경_할_수_있다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));

        final List<Name> newNames = names.stream().collect(Collectors.toUnmodifiableList());
        newNames.get(0).setValue("하이에나");
        final Name name = newNames.get(0);

        assertThat(name).hasFieldOrPropertyWithValue("value", "하이에나");

    }

    @Test
    void collectorsToUnmodifiable_방어적_복사를_한다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));
        final List<Name> newNames = names.stream().collect(Collectors.toUnmodifiableList());
        names.remove(0);

        assertThat(newNames).hasSize(1);
    }

    @Test
    void collectorsToUnmodifiable_깊은_방어적_복사를_하지_않는다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));
        final List<Name> newNames = names.stream().collect(Collectors.toUnmodifiableList());
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
