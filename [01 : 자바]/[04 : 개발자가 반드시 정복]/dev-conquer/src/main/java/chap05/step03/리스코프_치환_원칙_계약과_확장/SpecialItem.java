package chap05.step03.리스코프_치환_원칙_계약과_확장;

public class SpecialItem extends Item {

    @Override
    public boolean isDiscountAvailable() {
        return false;
    }
}
