package week0.gugudan;

public class Multiplication {
    private final int numberOne;
    private final int numberTwo;
    private final int result;

    public Multiplication(int numberOne, int numberTwo) {
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
        result = numberOne * numberTwo;
    }

    @Override
    public String toString() {

        return numberOne + " * " + numberTwo + " = " + result;
    }
}
