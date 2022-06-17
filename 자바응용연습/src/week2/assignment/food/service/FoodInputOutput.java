package week2.assignment.food.service;

import week2.assignment.food.console.FoodConsole;

import java.util.Scanner;

public class FoodInputOutput {

    private final FoodConsole foodConsole = new FoodConsole();
    private final Scanner sc = new Scanner(System.in);

    public String inputFoodNameThenOutput() {
        foodConsole.printAsPleaseEnterFoodName();
        return sc.nextLine();
    }

    public int inputFoodPriceThenOutput() {
        return Integer.parseInt(sc.nextLine());
    }

    public int inputFoodCountThenOutput() {
        return Integer.parseInt(sc.nextLine());
    }
}
