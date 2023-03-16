package item26;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Test01 {

    static class Example<T> {
        private final List<T> values = new ArrayList<>();

        public void add(T t) {
            values.add(t);
        }

        public List<T> getValues() {
            return values;
        }
    }

    @Test
    void 로_타입() {
        Example example = new Example<>();
        // 컴파일 에러
//        for (String value : example.getValues()) {
//            System.out.println(value);
//        }

    }

    @Test
    void 제네릭_타입() {
        Example<String> example = new Example<>();
        for (String value : example.getValues()) {
            System.out.println(value);
        }
    }
}
