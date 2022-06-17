package week2.lecture;


import java.util.Scanner;

/*

        Scanner sc = new Scanner(System.in);

        System.out.println("다음 세 가지 중 하나를 고르시오.");
        System.out.println("1.감자 2.옥수수 3.수박");

        int selectNum = sc.nextInt();
        String name = "";
        int price = 0;

        if (selectNum == 1) {
            name = "감자";

            System.out.println("1번 " + name + "을(를) 선택하셨습니다.");
            System.out.println(name + " 한 뭉텅이 어치를 입력해주세요.");
            System.out.println("1) 1,000원 어치 // 2) 2,000원 어치 // 3) 3,000원 어치");

            int selectPriceNum = sc.nextInt();

            if (selectPriceNum == 1) {
                price = 1000;
            }
            if (selectPriceNum == 2) {
                price = 2000;
            }
            if (selectPriceNum == 3) {
                price = 3000;
            }
        }

        if (selectNum == 2) {
            name = "옥수수";

            System.out.println("1번 " + name + "을(를) 선택하셨습니다.");
            System.out.println(name + " 한 뭉텅이 어치를 입력해주세요.");
            System.out.println("1) 4,000원 어치 // 2) 5,000원 어치 // 3) 6,000원 어치");

            int selectPriceNum = sc.nextInt();

            if (selectPriceNum == 1) {
                price = 4000;
            }
            if (selectPriceNum == 2) {
                price = 5000;
            }
            if (selectPriceNum == 3) {
                price = 6000;
            }
        }

        if (selectNum == 3) {
            name = "수박";

            System.out.println("1번 " + name + "을(를) 선택하셨습니다.");
            System.out.println(name + " 한 뭉텅이 어치를 입력해주세요.");
            System.out.println("1) 10,000원 어치 | 2) 20,000원 어치  3) | 30,000원 어치");

            int selectPriceNum = sc.nextInt();

            if (selectPriceNum == 1) {
                price = 10000;
            }
            if (selectPriceNum == 2) {
                price = 20000;
            }
            if (selectPriceNum == 3) {
                price = 30000;
            }
        }


        System.out.println(name + " 뭉텅이 당 " + price + "원 어치를 선택하셨습니다.");
        System.out.println("몇 뭉텅이를 주문하시겠습니까?");
        int orderCount = sc.nextInt();
        System.out.println(name + " " + orderCount + "뭉텅이는 총 " + orderCount * price + "원 입니다.");

 */


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int pocketMoney = 10000;

        System.out.println("철수와 영희는 " + pocketMoney + "원의 용돈을 가지고 있습니다.");
        System.out.print("철수와 영희 용돈을 추가해주세요 : ");
        pocketMoney += sc.nextInt();
        System.out.println("\n현재 철수와 영희의 용돈 총금액 : " + pocketMoney);


        int ddukbbokiPrice = 0;
        int udongPrice = 0;
        int snackPrice = 0;
        int hamburgerPrice = 0;
        int pizzaPrice = 0;

        System.out.print("\n떡볶이 금액을 입력해주세요 : ");
        ddukbbokiPrice = sc.nextInt();
        System.out.print("\n우동 금액을 입력해주세요 : ");
        udongPrice = sc.nextInt();
        System.out.print("\n과자 금액을 입력해주세요 : ");
        snackPrice = sc.nextInt();
        System.out.print("\n햄버거 금액을 입력해주세요 : ");
        hamburgerPrice = sc.nextInt();
        System.out.print("\n피자 금액을 입력해주세요 : ");
        pizzaPrice = sc.nextInt();

        int ddukbbokiCount = 0;
        int udongCount = 0;
        int snackCount = 0;
        int hamburgerCount = 0;
        int pizzaCount = 0;

        int originalPocketMoney = pocketMoney;


        System.out.print("\n떡볶이를 몇개 주문하시겠습니까?");
        ddukbbokiCount = sc.nextInt();

        if (pocketMoney - ddukbbokiPrice * ddukbbokiCount < 0) {
            System.out.println(ddukbbokiPrice * ddukbbokiCount - pocketMoney + "원의 용돈이 부족합니다.");
            System.out.print("추가해줄 용돈을 입력해주세요 : ");
            pocketMoney += sc.nextInt();
            System.out.println("\n현재 용돈은 " + pocketMoney + "원이 되었습니다.");
        }

        if (pocketMoney - ddukbbokiPrice * ddukbbokiCount > 0) {
            pocketMoney -= ddukbbokiPrice * ddukbbokiCount;
            originalPocketMoney -= ddukbbokiPrice * ddukbbokiCount;
            System.out.println("떡볶이는 총 " + ddukbbokiPrice * ddukbbokiCount + "원이었으며 현재 남은 용돈은 " + pocketMoney + "원 입니다.");


            System.out.print("\n우동을 몇개 주문하시겠습니까?");
            udongCount = sc.nextInt();

            if (pocketMoney - udongPrice * udongCount < 0) {
                System.out.println("우동을 시켜먹기에 " + (udongPrice * udongCount - pocketMoney) + "원이 부족합니다.");
                if (originalPocketMoney != pocketMoney) {
                    System.out.println("이미 용돈을 추가적으로 받았습니다. 때문에 주인장한테서 쫓겨났습니다.");
                }
                if (originalPocketMoney == pocketMoney) {
                    System.out.print("추가해줄 용돈을 입력해주세요 : ");
                    pocketMoney += sc.nextInt();
                    System.out.println("현재 용돈은 " + pocketMoney + "원이 되었습니다.");
                }
            }
            if (pocketMoney - udongPrice * udongCount >= 0) {
                pocketMoney -= udongPrice * udongCount;
                originalPocketMoney -= udongPrice * udongCount;
                System.out.println("우동은 총 " + udongPrice * udongCount + "원이었으며 현재 남은 용돈은 " + pocketMoney + "원 입니다.");


                System.out.print("\n과자을 몇개 주문하시겠습니까?");
                snackCount = sc.nextInt();

                if (pocketMoney - snackPrice * snackCount < 0) {
                    System.out.println("과자를 시켜먹기에 " + (snackPrice * snackCount - pocketMoney) + "원이 부족합니다.");
                    if (originalPocketMoney != pocketMoney) {
                        System.out.println("이미 용돈을 추가적으로 받았습니다. 때문에 주인장한테서 쫓겨났습니다.");
                    }
                    if (originalPocketMoney == pocketMoney) {
                        System.out.print("추가해줄 용돈을 입력해주세요 : ");
                        pocketMoney += sc.nextInt();
                        System.out.println("\n현재 용돈은 " + pocketMoney + "원이 되었습니다.");
                    }
                }
                if (pocketMoney - snackPrice * snackCount >= 0) {
                    pocketMoney -= snackPrice * snackCount;
                    originalPocketMoney -= snackPrice * snackCount;
                    System.out.println("과자는 총 " + snackPrice * snackCount + "원이었으며 현재 남은 용돈은 " + pocketMoney + "원 입니다.");


                    System.out.print("\n햄버거를 몇개 주문하시겠습니까?");
                    hamburgerCount = sc.nextInt();

                    if (pocketMoney - hamburgerPrice * hamburgerCount < 0) {
                        System.out.println("햄버거를 시켜먹기에 " + (hamburgerPrice * hamburgerCount - pocketMoney) + "원이 부족합니다.");
                        if (originalPocketMoney != pocketMoney) {
                            System.out.println("이미 용돈을 추가적으로 받았습니다. 때문에 주인장한테서 쫓겨났습니다.");
                        }
                        if (originalPocketMoney == pocketMoney) {
                            System.out.print("추가해줄 용돈을 입력해주세요 : ");
                            pocketMoney += sc.nextInt();
                            System.out.println("\n현재 용돈은 " + pocketMoney + "원이 되었습니다.");
                        }
                    }
                    if (pocketMoney - hamburgerPrice * hamburgerCount >= 0) {
                        pocketMoney -= hamburgerPrice * hamburgerCount;
                        originalPocketMoney -= hamburgerPrice * hamburgerCount;
                        System.out.println("햄버거는 총 " + hamburgerPrice * hamburgerCount + "원이었으며 현재 남은 용돈은 " + pocketMoney + "원 입니다.");


                        System.out.print("\n피자를 몇개 주문하시겠습니까?");
                        pizzaCount = sc.nextInt();

                        if (pocketMoney - pizzaPrice * pizzaCount < 0) {
                            System.out.println("피자를 시켜먹기에 " + (pizzaPrice * pizzaCount - pocketMoney) + "원이 부족합니다.");
                            if (originalPocketMoney != pocketMoney) {
                                System.out.println("이미 용돈을 추가적으로 받았습니다. 때문에 주인장한테서 쫓겨났습니다.");
                            }
                            if (originalPocketMoney == pocketMoney) {
                                System.out.print("추가해줄 용돈을 입력해주세요 : ");
                                pocketMoney += sc.nextInt();
                                System.out.println("\n현재 용돈은 " + pocketMoney + "원이 되었습니다.");
                            }
                        }
                        if (pocketMoney - pizzaPrice * pizzaCount >= 0) {
                            pocketMoney -= pizzaPrice * pizzaCount;
                            originalPocketMoney -= pizzaPrice * pizzaCount;
                            System.out.println("피자는 총 " + pizzaPrice * pizzaCount + "원이었으며 현재 남은 용돈은 " + pocketMoney + "원 입니다.");
                        }


                    }
                }
            }
        }


    }
}























