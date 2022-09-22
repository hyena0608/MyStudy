package week4.lecture;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        int ONE = 1;
        String nameOne = null;
        int scoreOne = 0;

        int TWO = 2;
        String nameTwo = null;
        int scoreTwo = 0;

        int THREE = 3;
        String nameThree = null;
        int scoreThree = 0;

        int FOUR = 4;
        String nameFour = null;
        int scoreFour = 0;


        Scanner sc = new Scanner(System.in);
        int n = 0;
        while (n <= 4) {
            n++;
            if (n == ONE) {
                System.out.print("첫 번째 사람 이름 : ");
                nameOne = sc.nextLine();
                System.out.print(nameOne + "이가 받은 점수 입력 : ");
                scoreOne = Integer.parseInt(sc.nextLine());
            }
            if (n == TWO) {
                System.out.print("두 번째 사람 이름 : ");
                nameTwo = sc.nextLine();
                System.out.print(nameTwo + "이가 받은 점수 입력 : ");
                scoreTwo = Integer.parseInt(sc.nextLine());
            }
            if (n == THREE) {
                System.out.print("세 번째 사람 이름 : ");
                nameThree = sc.nextLine();
                System.out.print(nameThree + "이가 받은 점수 입력 : ");
                scoreThree = Integer.parseInt(sc.nextLine());
            }
            if (n == FOUR) {
                System.out.print("네 번째 사람 이름 : ");
                nameFour = sc.nextLine();
                System.out.print(nameFour + "이가 받은 점수 입력 : ");
                scoreFour = Integer.parseInt(sc.nextLine());
            }
        }

        System.out.println("1. " + nameOne);
        System.out.println("2. " + nameTwo);
        System.out.println("3. " + nameThree);
        System.out.println("4. " + nameFour);
        System.out.println("5.  종료");

        System.out.println("누구와 누구를 대조하시겠습니까?");


        while (true) {
            int numOne = 0;
            int numTwo = 0;

            System.out.print("첫 번째 사람 입력 : ");
            numOne = sc.nextInt();
            System.out.print("두 번째 사람 입력 : ");
            numTwo = sc.nextInt();

            String firstName = null;
            int firstScore = 0;
            String secondName = null;
            int secondScore = 0;

            if (numOne == ONE) {
                firstName = nameOne;
                firstScore = scoreOne;
            }
            if (numOne == TWO) {
                firstName = nameTwo;
                firstScore = scoreTwo;
            }
            if (numOne == THREE) {
                firstName = nameThree;
                firstScore = scoreThree;
            }
            if (numOne == FOUR) {
                firstName = nameFour;
                firstScore = scoreFour;
            }
            if (numTwo == ONE) {
                secondName = nameOne;
                secondScore = scoreOne;
            }
            if (numTwo == TWO) {
                secondName = nameTwo;
                secondScore = scoreTwo;
            }
            if (numTwo == THREE) {
                secondName = nameThree;
                secondScore = scoreThree;
            }
            if (numTwo == FOUR) {
                secondName = nameFour;
                secondScore = scoreFour;
            }

            if (numOne == 5 && numTwo == 5) {
                System.out.println("종료합니다.");
                break;
            }
            if (numOne == 5 && numTwo != 5) {
                System.out.println("잘못된 입력입니다.");
            }
            if (numOne != 5 && numTwo == 5) {
                System.out.println("잘못된 입력입니다.");
            }

            if (numOne != 5 && numTwo != 5) {
                if (firstScore > secondScore) {
                    System.out.println("시스템 : " + firstName + "의 점수가 " + secondName + "의 점수보다 더 큽니다.");
                }
                if (secondScore > firstScore) {
                    System.out.println("시스템 : " + secondName + "의 점수가 " + firstName + "의 점수보다 더 큽니다.");
                }
            }
        }


    }


}
