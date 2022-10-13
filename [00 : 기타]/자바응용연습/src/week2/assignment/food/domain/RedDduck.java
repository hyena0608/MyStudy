package week2.assignment.food.domain;

public class RedDduck implements Food {

    private final String name = "떡볶이";
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
