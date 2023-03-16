package item42;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Test01 {

    @Test
    void 잘못된_결과_반환() {
        MyObject myObject = new MyObject(0);
        method_A(myObject, 1);        // method_A -> method_B -> method_C -> method_D -> method_EXCEPTION

        Integer sum = myObject.getValue();      // 예상 결과 = 5
        System.out.println("sum = " + sum);     // 실제 결과 = 3
    }

    private void method_A(MyObject value, Integer addValue) {
        value.add(addValue);                    // 1을 더한다.
        method_B(value, addValue);              // 매개변수 1을 넘긴다.
    }

    private void method_B(MyObject value, Integer addValue) {
        value.add(addValue);                    // 1을 더한다.
        method_C(value, addValue);              // 매개변수 1을 넘긴다.
    }

    private void method_C(MyObject value, Integer addValue) {
        value.add(addValue);                    // 1을 더한다.
        method_D(value, addValue);              // 매개변수 1을 넘긴다.
    }

    private void method_D(MyObject value, Integer addValue) {
        value.add(addValue);                    // 1을 더한다.
        method_EXCEPTION(value, -1);  // 잘못된 매개변수를 넘긴다.
    }

    private MyObject method_EXCEPTION(MyObject value, Integer addValue) {
        value.add(addValue);                    // -1을 더한다.
        return value;                           // 잘못 계산된 MyObject를 반환한다.
    }

    private static class MyObject {
        private Integer value;

        public MyObject(final Integer value) {
            this.value = value;
        }

        public void add(Integer value) {
            this.value += value;
        }

        public Integer getValue() {
            return value;
        }
    }

    @Test
    void 예상하지_못한_상황() {
        Deck deck = new Deck(initData());
        String card = deck.draw();

        System.out.println("card = " + card);
    }

    class Deck {
        private final List<String> cards;

        public Deck(final List<String> cards) {
            this.cards = cards;
        }

        public String draw() {
            if (cards.isEmpty()) {
                throw new IllegalStateException("카드를 모두 소비하였습니다.");
            }
            return cards.remove(0);
        }
    }

    private List<String> initData() {
        return List.of();
    }

    @Test
    void 예외를_던지면서_애플리케이션_동작이_중단된다() {
        DeckV2 deck = new DeckV2(initData_Size10());

        printDrawCards(deck, 10);
    }

    class DeckV2 {

        private final List<String> cards;

        public DeckV2(final List<String> cards) {
            this.cards = cards;
        }

        public String draw() {
            String card = cards.remove(0);
            if (!card.contains("카드")) {
                throw new IllegalStateException("잘못된 카드 형식입니다.");
            }
            return card;
        }

    }

    private List<String> initData_Size10() {
        return new ArrayList<>(List.of("카드1", "카드2", "카드3", "카드4", "카드5", "카드6", "카드7", "카드8", "카드9"));
    }

    private void printDrawCards(DeckV2 deck, int deckSize) {
        for (int count = 0; count < deckSize; count++) {
            System.out.println("card = " + deck.draw());
        }
    }


    @Test
    void 메서드_몸체_실행_전_매개변수를_검사한다() {
        DeckV3 deck = new DeckV3(initData());
        String card = deck.draw();

        System.out.println("card = " + card);
    }

    class DeckV3 {
        private final List<String> cards;

        public DeckV3(final List<String> cards) {
            if (cards.isEmpty()) {
                throw new IllegalArgumentException("카드가 비어있을 수 없습니다.");
            }
            this.cards = cards;
        }

        public String draw() {
            if (cards.isEmpty()) {
                throw new IllegalStateException("카드를 모두 소비하였습니다.");
            }
            return cards.remove(0);
        }
    }

    @Test
    void 매개변수가_잘못되면_예외가_발생한다() {
        MyObject myObject = new MyObject(0);
        // method_A_V2 -> method_B_V2 -> method_C_V2 -> method_D_V2 -> method_EXCEPTION_V2 (예외 발생)
        method_A_V2(myObject, 1);
    }

    private void method_A_V2(MyObject value, Integer addValue) {
        validateValueIsOne(addValue);
        value.add(addValue);                       // 1을 더한다.
        method_B_V2(value, addValue);              // 매개변수 1을 넘긴다.
    }

    private void method_B_V2(MyObject value, Integer addValue) {
        validateValueIsOne(addValue);
        value.add(addValue);                       // 1을 더한다.
        method_C_V2(value, addValue);              // 매개변수 1을 넘긴다.
    }

    private void method_C_V2(MyObject value, Integer addValue) {
        validateValueIsOne(addValue);
        value.add(addValue);                       // 1을 더한다.
        method_D_V2(value, addValue);              // 매개변수 1을 넘긴다.
    }

    private void method_D_V2(MyObject value, Integer addValue) {
        validateValueIsOne(addValue);
        value.add(addValue);                        // 1을 더한다.
        method_EXCEPTION_V2(value, -1);   // 잘못된 매개변수를 넘긴다.
    }

    private MyObject method_EXCEPTION_V2(MyObject value, Integer addValue) {
        validateValueIsOne(addValue);
        value.add(addValue);                        // -1을 더한다.
        return value;                               // 잘못 계산된 MyObject를 반환한다.
    }

    private void validateValueIsOne(Integer value) {
        if (value != 1) {
            throw new IllegalArgumentException("value는 1이어야 합니다.");
        }
    }

    @Test
    void 생성은_성공하지만_정상적인_동작을_하지_않는다() {
        System.out.println("=== DeckV4 생성 이전 ===");
        DeckV4 deck = new DeckV4(initDataV4()); // 생성에 성공한다.
        System.out.println("=== DeckV4 생성 이후 ===");

        System.out.println("=== draw 이전 ===");
        deck.draw(); // === 동작 실패한다. ===
        System.out.println("=== draw 이후 ===");
    }

    class DeckV4 {
        private final List<String> cards;

        public DeckV4(final List<String> cards) {
            this.cards = cards;
        }

        public String draw() {
            return cards.remove(0); // === 예외가 발생한다. ===
        }
    }

    private List<String> initDataV4() {
        return null; // [X] null을 반환한다.
    }

    @Test
    void 생성_실패한다() {
        System.out.println("=== DeckV5 생성 이전 ===");
        DeckV5 deck = new DeckV5(initDataV5()); // === 생성 실패한다. ===
        System.out.println("=== DeckV5 생성 이후 ===");

        System.out.println("=== draw 이전 ===");
        deck.draw(); // 앞단에서 생성 실패하여 메서드가 호출되지 않는다.
        System.out.println("=== draw 이후 ===");
    }

    class DeckV5 {
        private final List<String> cards;

        public DeckV5(final List<String> cards) {
            if (cards == null) {
                throw new IllegalArgumentException("cards는 null일 수 없습니다."); // === 예외가 발생한다. ===
            }
            this.cards = cards;
        }

        public String draw() {
            return cards.remove(0);
        }
    }

    private List<String> initDataV5() {
        return null; // [x] null을 반환한다.
    }

    @Test
    void 생성_실패한다_V2() {
        System.out.println("=== DeckV6 생성 이전 ===");
        DeckV6 deck = new DeckV6(initDataV6()); // === 생성 실패한다. ===
        System.out.println("=== DeckV6 생성 이후 ===");

        System.out.println("=== draw 이전 ===");
        deck.draw(); // 앞단에서 생성 실패하여 메서드가 호출되지 않는다.
        System.out.println("=== draw 이후 ===");
    }

    class DeckV6 {
        private final List<String> cards;

        // public DeckV6(final List<String> cards) {
        //     this.cards = Objects.requireNonNull(cards, "카드가 null일 수 없습니다.");
        // }

        public DeckV6(final List<String> cards) {
            Objects.requireNonNull(cards, "카드가 null일 수 없습니다.");
            if (cards.isEmpty()) {
                throw new IllegalArgumentException("카드는 비어있을 수 없습니다.");
            }
            this.cards = cards;
        }

        public String draw() {
            return cards.remove(0);
        }
    }

    private List<String> initDataV6() {
        return null; // [X] null을 반환한다.
    }

    @Test
    void assert는_조건에_충족하지_않으면_프로그램을_종료한다() {
        // assert 조건문 : 출력 메시지
        assert false : "프로그램이 진행되지 않습니다.";
    }

    @Test
    void DeckV7_assert문을_이용한다() {
        new DeckV7(null);
    }

    class DeckV7 {
        private final List<String> cards;

        public DeckV7( List<String> cards) {
            try {

                assert cards != null : "카드는 null일 수 없습니다.";
                assert !cards.isEmpty() : "카드는 비어있을 수 없습니다.";

            } catch (AssertionError e) {
                cards = List.of("ssss");
            }
            System.out.println("cards = " + cards);
            this.cards = cards;
        }

        public String draw() {
            assert cards != null : "카드는 null일 수 없습니다.";
            assert !cards.isEmpty() : "카드는 비어있을 수 없습니다.";
            return cards.remove(0);
        }
    }

}
