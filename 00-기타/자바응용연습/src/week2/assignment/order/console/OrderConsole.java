package week2.assignment.order.console;

import week2.assignment.food.domain.Food;

public class OrderConsole {

    public void printAfterOrderFood(Food food, int count, int totalPrice) {
        System.out.println(food.getName()
                + "을(를) "
                + count
                + "개 구매하여 "
                + totalPrice
                + "원의 비용이 청구됩니다.");
    }
}
