package chap05.step03.리스코프_치환_원칙_계약과_확장;

/**
 * LSP 를 어기면 OCP를 어길 가능성이 높아진다.
 * 하위 타입에서 명세에서 벗어난 동작을 하면,
 * 구현한 코드는 비정상적으로 동작할 수 있다.
 */
public class Coupon {
    private int discountRate;

    /**
     * Item을 상속한 SpecialItem 클래스를 작성한다.
     * 변화되는 부분(isDiscountAvailable)을 상위 타입에 추가함으로써
     * instanceof 연산자를 사용하던 코드를 Item 클래스만 사용하도록 구현할 수 있다.
     */
    public int calculateDiscountAmount(Item item) {
        if (!item.isDiscountAvailable()) {
            return 0;
        }
        return item.getPrice() * discountRate;
    }
}
