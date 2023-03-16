package item31;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ItemExample {

    @Test
    void 와일드카드_타입을_사용하지_않은_pushAll_메서드() {
        MyStack<Number> myStack = new MyStack<>();
        Iterable<Integer> integers = new ArrayList<>();
        // assertThatNoException().isThrownBy(() -> myStack.pushAllV1(integers)); // ERROR, 매개변수화 타입 불공변
    }

    @Test
    void 생산자_매개변수_와일드카드_타입_적용() {
        MyStack<Number> myStack = new MyStack<>();
        Iterable<Integer> integers = new ArrayList<>();
        assertThatNoException().isThrownBy(() -> myStack.pushAllV2(integers));
    }

    @Test
    void 와일드카드_타입을_사용하지_않는_popAll() {
        MyStack<Number> numberStack = new MyStack<>();
        Collection<Object> objects = new ArrayList<>();
        // numberStack.popAllV1(objects); // ERROR, Collection<Object>는 Collection<Number>의 하위 타입이 아니다.
    }

    @Test
    void 와일드카드_타입을_적용한_popAll() {
        MyStack<Number> numberStack = new MyStack<>();
        Collection<Object> objects = new ArrayList<>();

        assertThatNoException().isThrownBy(() -> numberStack.popAllV2(objects));
    }

    private static class MyStack<E> {

        private Stack<E> myStack;

        public MyStack() {
            this.myStack = new Stack<>();
        }

        public void push(E e) {
            myStack.push(e);
        }

        public E pop() {
            return myStack.pop();
        }

        public boolean isEmpty() {
            return myStack.isEmpty();
        }

        public void pushAllV1(Iterable<E> src) {
            for (E e : src) {
                push(e);
            }
        }

        /**
         * E의 상위 타입의 Iterable 이어야 한다.
         */
        public void pushAllV2(Iterable<? extends E> src) {
            for (E e : src) {
                push(e);
            }
        }

        public void popAllV1(Collection<E> dst) {
            while (!isEmpty()) {
                dst.add(pop());
            }
        }

        /**
         * E의 상위 타입의 Collection 이어야 한다.
         */
        public void popAllV2(Collection<? super E> dst) {
            while (!isEmpty()) {
                dst.add(pop());
            }
        }


    }

    @Test
    void swapV1() {
        List<String> values = new ArrayList<>();
        values.add("가");
        values.add("나");
        swap(values, 0, 1);
        System.out.println(values);
    }

    private <E> void swap(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }


}
