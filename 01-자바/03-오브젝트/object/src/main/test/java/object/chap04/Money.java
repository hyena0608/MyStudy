package java.object.chap04;

public class Money {
    public static final Money ZERO = new Money(0);

    private final int value;

    public Money(final int value) {
        this.value = value;
    }

    public Money minus(final Money discountAmount) {
        return new Money(this.value - discountAmount.value);
    }

    public Money times(final double discountPercent) {
        return new Money((int) (this.value - this.value * discountPercent));
    }
}
