package week2.assignment.food.domain;

public class Snack implements Food {

    private final String name = "과자";
    private int price;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }


}
