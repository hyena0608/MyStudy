package week2.assignment.food.console;

import week2.assignment.food.domain.Food;

import java.util.List;

public class FoodConsole {

    public void printWhenSettingPrice(Food food) {
        System.out.print(food.getName() + "의 가격을 입력해주세요 : ");
    }

    public void printWhentSettingFoodCount(Food food) {
        System.out.print("\n" + food.getName() + "의 주문 수량을 입력해주세요 : ");
    }

    public void printAsPleaseEnterFoodName() {
        System.out.print("\n추가하실 음식의 이름을 입력해주세요 : ");
    }

    public void printFoodList(List<Food> foodList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n현재 음식은 ");
        foodList.forEach(
                food -> stringBuffer.append(food.getName()).append(", ")
        );
        stringBuffer.append("가 존재합니다.");

        System.out.println(stringBuffer.toString());
    }
}
