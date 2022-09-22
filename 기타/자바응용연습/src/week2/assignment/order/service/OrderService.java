package week2.assignment.order.service;

import week2.assignment.food.domain.Food;
import week2.assignment.food.service.FoodService;
import week2.assignment.member.domain.MemberStorage;
import week2.assignment.money.service.MoneyService;
import week2.assignment.order.console.OrderConsole;

public class OrderService {

    private final OrderConsole orderConsole = new OrderConsole();

    FoodService foodService = new FoodService();
    MoneyService moneyService = new MoneyService();

    public void orderFood(MemberStorage memberStorage, Food food) {
        int count = foodService.scannerFoodCount(food);
        int totalPrice = food.getPrice() * count;
        orderConsole.printAfterOrderFood(food, count, totalPrice);
        moneyService.spendMoney(memberStorage.getPocketMoney(), totalPrice);
    }

}
