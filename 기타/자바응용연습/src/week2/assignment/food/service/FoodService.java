package week2.assignment.food.service;

import week2.assignment.food.domain.*;
import week2.assignment.food.console.FoodConsole;

import java.util.List;

public class FoodService {

    private final AdditionalFoodService additionalFoodService = new AdditionalFoodService();
    private final FoodConsole foodConsole = new FoodConsole();
    private final FoodInputOutput foodInputOutput = new FoodInputOutput();


    public void settingAllKindsOfFoodsPrice() {
        AdditionalFoodStorage.getFoodList().forEach(food -> settingFoodPrice(food));
    }

    public void settingInitFoods() {
        settingAlreadyExistsFoods();
        settingAddtionalFoods();
    }

    private List<Food> settingAlreadyExistsFoods() {
        List<Food> foodList = AdditionalFoodStorage.getFoodList();
        foodList.add(new RedDduck());
        foodList.add(new Snack());
        foodList.add(new Udong());
        foodConsole.printFoodList(foodList);
        return foodList;
    }

    private void settingAddtionalFoods() {
        additionalFoodService.createFoods();
    }

    private void settingFoodPrice(Food food) {
        foodConsole.printWhenSettingPrice(food);
        int price = foodInputOutput.inputFoodPriceThenOutput();
        food.setPrice(price);
    }

    public int scannerFoodCount(Food food) {
        foodConsole.printWhentSettingFoodCount(food);
        int count = foodInputOutput.inputFoodCountThenOutput();
        return count;
    }
}

