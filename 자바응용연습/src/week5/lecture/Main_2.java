package week5.lecture;

import java.util.Scanner;

public class Main_2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("개미 세마리 위치와 방향을 알려주세요");

        System.out.print("첫 번째 개미 위치를 알려주세요 (0 ~ 24) : ");
        int antOne = Integer.parseInt(sc.nextLine());
        System.out.print("첫 번째 개미 방향을 알려주세요 -1. L / 1. R : ");
        int antOnePoint = Integer.parseInt(sc.nextLine());

        System.out.print("두 번째 개미 위치를 알려주세요 (0 ~ 24) : ");
        int antTwo = Integer.parseInt(sc.nextLine());
        System.out.print("두 번째 개미 방향을 알려주세요 -1. L / 1. R : ");
        int antTwoPoint = Integer.parseInt(sc.nextLine());

        System.out.print("세 번째 개미 위치를 알려주세요 (0 ~ 24) : ");
        int antThree = Integer.parseInt(sc.nextLine());
        System.out.print("세 번째 개미 방향을 알려주세요 -1. L / 11. R : ");
        int antThreePoint = Integer.parseInt(sc.nextLine());

        // (0, 경기중) (1, 경기끝)
        int antOneEnd = 0;
        int antTwoEnd = 0;
        int antThreeEnd = 0;


        int count = 1;

        while (true) {

            // 방향 (-1, LEFT) (1, RIGHT)
            if (antOneEnd == 0 && antTwoEnd == 0) {
                // 충돌 했을 때
                // 원상복귀, 방향 변경
                if (antOne + antOnePoint == antTwo + antTwoPoint) {
                    antOnePoint *= -1;
                    antTwoPoint *= -1;
                }
                // 방향 바꿔서 앞으로 갔을 때 갔지 않다면 기존 방향으로 이동
                if (antOne - antOnePoint != antTwo - antTwoPoint) {
                    antOne += antOnePoint;
                    antTwo += antTwoPoint;
                }
            }

            if (antOneEnd == 0 && antThreeEnd == 0) {
                if (antOne + antOnePoint == antThree + antThreePoint) {
                    antOnePoint *= -1;
                    antThreePoint *= -1;
                }
                if (antOne - antOnePoint != antThree - antThreePoint) {
                    antOne += antOnePoint;
                    antThree += antThreePoint;
                }
            }

            if (antTwoEnd == 0 && antThreeEnd == 0) {
                if (antTwo + antTwoPoint == antThree + antThreePoint) {
                    antTwoPoint *= -1;
                    antThreePoint *= -1;
                }
                if (antTwo - antTwoPoint != antThree - antThreePoint) {
                    antTwo += antTwoPoint;
                    antThree += antThreePoint;
                }
            }

            if (antOneEnd == 0) {
                if (antOne <= 0 || antOne >= 24) {
                    System.out.println("첫 번째 개미가 " + count++ + "등입니다.");
                    antOneEnd = 1;
                }
            }

            if (antTwoEnd == 0) {
                if (antTwo <= 0 || antTwo >= 24) {
                    System.out.println("두 번째 개미가 " + count++ + "등입니다.");
                    antTwoEnd = 1;
                }
            }

            if (antThreeEnd == 0) {
                if (antThree <= 0 || antThree >= 24) {
                    System.out.println("세 번째 개미가 " + count++ + "등입니다.");
                    antThreeEnd = 1;
                }
            }

            if (count == 3) {
                break;
            }
        }

        if (antOneEnd == 0) {
            System.out.println("첫 번째 개미가 " + count + "등입니다.");
        }
        if (antTwoEnd == 0) {
            System.out.println("두 번째 개미가 " + count + "등입니다.");
        }
        if (antThreeEnd == 0) {
            System.out.println("세 번째 개미가 " + count + "등입니다.");
        }

    }
}
