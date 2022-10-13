package week2.assignment;

import week2.assignment.food.domain.AdditionalFoodStorage;
import week2.assignment.food.service.FoodService;
import week2.assignment.member.domain.Member;
import week2.assignment.member.domain.MemberStorage;
import week2.assignment.member.service.MemberService;
import week2.assignment.money.domain.PocketMoney;
import week2.assignment.money.service.MoneyService;
import week2.assignment.order.service.OrderService;

import java.util.List;


public class Application {

    public static void main(String[] args) {

        final FoodService foodService = new FoodService();
        final MemberService memberService = new MemberService();
        final MoneyService moneyService = new MoneyService();
        final OrderService orderService = new OrderService();

        // 손님 생성
        List<Member> memberList = memberService.createMemberList();

        // 용돈 생성
        PocketMoney pocketMoney = new PocketMoney(10000);

        // 손님들과 공동 용돈 저장소 생성
        MemberStorage memberStorage = new MemberStorage(memberList, pocketMoney);

        // 손님 추가 용돈 (1)
        moneyService.addMoreMoney(pocketMoney);

        // 음식 생성
        foodService.settingInitFoods();

        // 음식 가격 조정
        foodService.settingAllKindsOfFoodsPrice();

        // 음식 주문
        AdditionalFoodStorage.getFoodList().forEach(
                food -> orderService.orderFood(memberStorage, food)
        );
    }
}
