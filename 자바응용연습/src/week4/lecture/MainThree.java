package week4.lecture;

import java.util.Scanner;

public class MainThree {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalPrice = 0;
        for (int i = 1;; i++) {
            System.out.println("1.장어덮밥(5000원) 2.옥수수콘(10000원) 3.감자튀김(3000원) 4.총 가격");
            System.out.print("선택할 번호 : ");
            int selectNum = Integer.parseInt(sc.nextLine());
            if (selectNum == 1) {
                System.out.println("1번 장어덮밥을 선택하셨습니다.");
                totalPrice += 5000;
            }
            if (selectNum == 2) {
                System.out.println("2번 옥수수콘을 선택하셨습니다.");
                totalPrice += 10000;
            }
            if (selectNum == 3) {
                System.out.println("3번 감자튀김을 선택하셨습니다.");
                totalPrice += 3000;
            }
            if (selectNum == 4) {
                System.out.println("감사합니다. 총 가격은 " + totalPrice + "원입니다.");
                System.out.println("계속 구매하시겠다면 1번, 아니면 2번을 눌러주세요.");
                System.out.print("선택할 번호 : ");
                int continueNum = Integer.parseInt(sc.nextLine());
                if (continueNum == 1) {
                    System.out.println("계속 진행합니다.");
                }
                if (continueNum == 2) {
                    System.out.println(i + "번 째 손님이 " + totalPrice + "원 계산을 했습니다.");
                    System.out.println("다음 손님으로 넘어갑니다.");
                    System.out.println("==============================================================================");
                    totalPrice = 0;
                }
            }
        }
    }
}
