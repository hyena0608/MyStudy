package week2.assignment.food.domain;

import java.util.ArrayList;
import java.util.List;

public class AdditionalFoodStorage {

    static private List<Food> foodList = new ArrayList<>();

    public static List<Food> getFoodList() {
        return foodList;
    }

}
