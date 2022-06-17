package week2.assignment.food.domain;

public class Udong implements Food {

    private final String name = "우동";
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

