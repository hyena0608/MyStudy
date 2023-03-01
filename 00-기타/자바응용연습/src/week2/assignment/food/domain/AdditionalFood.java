package week2.assignment.food.domain;

public class AdditionalFood implements Food {

    private String name;
    private int price;

    public AdditionalFood(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPrice() {
        if (price == 0) {
            throw new NullPointerException(name + "의 금액이 정해지지 않았습니다.");
        }
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

}
