package chap05.step03.리스코프_치환_원칙_계약과_확장;

public class Item {

    private int price;

    public boolean isDiscountAvailable() {
        return true;
    }

    public int getPrice() {
        return price;
    }
}
