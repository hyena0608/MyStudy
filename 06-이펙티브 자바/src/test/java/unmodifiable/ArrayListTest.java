package unmodifiable;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayListTest {
    
    @Test
    void newArrayList_리스트는_정렬_할_수_있다() {
        final List<Integer> values = new ArrayList<>(List.of(1, 3, 2));
        final List<Integer> newValues = new ArrayList<>(values);
        Collections.sort(newValues);

        assertThat(newValues).containsExactlyElementsOf(List.of(1, 2, 3));
    }

    @Test
    void newArrayList_리스트는_데이터를_추가_할_수_있다() {
        final List<Integer> values = new ArrayList<>();
        final List<Integer> newValues = new ArrayList<>(values);
        newValues.add(1);
        
        assertThat(newValues).hasSize(1);
    }

    @Test
    void newArrayList_리스트는_데이터를_삭제_할_수_있다() {
        final List<Integer> values = (new ArrayList<>());
        final ArrayList<Integer> newValues = new ArrayList<>(values);
        newValues.add(0);
        newValues.remove(0);

        assertThat(newValues).isEmpty();
    }

    @Test
    void newArrayList_리스트는_내부_데이터를_변경_할_수_있다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));

        final List<Name> newNames = new ArrayList<>(names);
        newNames.get(0).setValue("하이에나");
        final Name name = newNames.get(0);

        assertThat(name).hasFieldOrPropertyWithValue("value", "하이에나");

    }

    @Test
    void newArrayList_리스트는_방어적_복사를_한다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));
        final List<Name> newNames = new ArrayList<>(names);
        names.remove(0);

        assertThat(newNames).hasSize(1);
    }

    @Test
    void newArrayList_리스트는_깊은_방어적_복사를_하지_않는다() {
        final List<Name> names = new ArrayList<>();
        names.add(new Name("헤나"));
        final List<Name> newNames = new ArrayList<>(names);
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
