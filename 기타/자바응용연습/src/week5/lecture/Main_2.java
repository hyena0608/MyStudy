package week5.lecture;

import java.util.Scanner;

public class Main_2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("개미 세마리 위치와 방향을 알려주세요");

        System.out.print("첫 번째 개미 위치를 알려주세요 (0 < x <24) : ");
        int antOne = Integer.parseInt(sc.nextLine());
        System.out.print("첫 번째 개미 방향을 알려주세요 -1. L / 1. R : ");
        int antOnePoint = Integer.parseInt(sc.nextLine());

        System.out.print("두 번째 개미 위치를 알려주세요 (0 < x < 24) : ");
        int antTwo = Integer.parseInt(sc.nextLine());
        System.out.print("두 번째 개미 방향을 알려주세요 -1. L / 1. R : ");
        int antTwoPoint = Integer.parseInt(sc.nextLine());

        System.out.print("세 번째 개미 위치를 알려주세요 (0 < x < 24) : ");
        int antThree = Integer.parseInt(sc.nextLine());
        System.out.print("세 번째 개미 방향을 알려주세요 -1. L / 1. R : ");
        int antThreePoint = Integer.parseInt(sc.nextLine());

        // (0, 경기중) (1, 경기끝)
        int antOneEnd = 0;
        int antTwoEnd = 0;
        int antThreeEnd = 0;

        int count = 0;

        while (true) {
            if ((antOne == 0 || antOne == 24) && antOneEnd == 0) {
                System.out.println("첫 번째 개미가 " + ++count + "등입니다.");
                antOneEnd = 1;
            }

            if ((antTwo == 0 || antTwo == 24) && antTwoEnd == 0) {
                System.out.println("두 번째 개미가 " + ++count + "등입니다.");
                antTwoEnd = 1;
            }

            if ((antThree == 0 || antThree == 24) && antThreeEnd == 0) {
                System.out.println("세 번째 개미가 " + ++count + "등입니다.");
                antThreeEnd = 1;
            }

            if ((antOne == 0 || antOne == 24)
                    && (antTwo == 0 || antTwo == 24)
                    && (antThree == 0 || antThree == 24)) {
                System.out.println("경기를 종료합니다.");
                break;
            }

            // 개미 이동
            if (antOneEnd == 0) {
                antOne += antOnePoint;
            }
            if (antTwoEnd == 0) {
                antTwo += antTwoPoint;
            }
            if (antThreeEnd == 0) {
                antThree += antThreePoint;
            }

            // 개미 교차한 경우
            if ((antOneEnd == 0 && antTwoEnd == 0) && antOne == antTwo - antTwoPoint) {
                antOnePoint *= -1;
                antTwoPoint *= -1;
                antOne += antOnePoint;
                antTwo += antTwoPoint;
            }

            if ((antOneEnd == 0 && antThreeEnd == 0) && antOne == antThree - antThreePoint) {
                antOnePoint *= -1;
                antThreePoint *= -1;
                antOne += antOnePoint;
                antThree += antThreePoint;
            }

            if ((antTwoEnd == 0 && antThreeEnd == 0) && antTwo == antThree - antThreePoint) {
                antTwoPoint *= -1;
                antThreePoint *= -1;
                antTwo += antTwoPoint;
                antThree += antThreePoint;
            }
        }
    }
}
