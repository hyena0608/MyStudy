package week2.assignment.food.service;

import week2.assignment.food.domain.AdditionalFood;
import week2.assignment.food.domain.AdditionalFoodStorage;
import week2.assignment.food.domain.Food;
import week2.assignment.food.console.AdditionalFoodConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdditionalFoodService {

    private final AdditionalFoodConsole console = new AdditionalFoodConsole();
    private final FoodInputOutput foodInputOutput = new FoodInputOutput();

    public void createFoods() {
        List<Food> foodList = new ArrayList<>();

        do {
            foodList.add(createFood());

            console.printWantToAddMoreFood();
            String answer = new Scanner(System.in).nextLine();

            if (!isYes(answer)) {
                console.printEndAddFoodMode();
                break;
            }
        } while (true);

        addToStorage(foodList);
    }

    private Food createFood() {
        String name = foodInputOutput.inputFoodNameThenOutput();
        return new AdditionalFood(name);
    }

    private boolean isYes(String answer) {
        if (answer.equals("y")) {
            return true;
        } else if (answer.equals("n")) {
            return false;
        }
        return false;
    }

    private void addToStorage(List<Food> foodList) {
        foodList.forEach(
                food -> AdditionalFoodStorage.getFoodList().add(food)
        );

    }
}
