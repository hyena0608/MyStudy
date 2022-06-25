package week3.lecture;

import java.util.Scanner;

public class While {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        System.out.print("첫 번째 숫자를 입력해주세요. : ");
        int firstNum = sc.nextInt();
        System.out.print("두 번째 숫자를 입력해주세요. : ");
        int secondNum = sc.nextInt();

        System.out.println("정답을 입력해주세요.");
        System.out.print(firstNum + " + " + secondNum + " = ");
        int answer = firstNum + secondNum;

        int count = 0;
        int finalChance = 0;
        int scannedAnswer = 0;
        while (true) {
            scannedAnswer = sc.nextInt();

            if (scannedAnswer == answer) {
                System.out.println("정답입니다.");
                break;
            }

            if (scannedAnswer != answer) {
                System.out.println("오답입니다.");
                if (count < 10) {
                    count++;
                }
                if (count == 10) {
                    if (finalChance < 3) {
                        finalChance++;
                        count = 0;
                    }
                    if (finalChance == 3) {
                        break;
                    }
                }
            }
        }


        int secondScannedAnswer = 0;
        int wrongCount = 0;
        int solved = 0;
        while (true) {

            System.out.println("둘 중 어떤 숫자가 더 큰가요 ?");
            System.out.println("1." + firstNum);
            System.out.println("2. " + secondNum);
            System.out.print("정답을 입력해주세요 : ");

            secondScannedAnswer = sc.nextInt();

            if (firstNum > secondNum) {
                if (secondScannedAnswer == 1) {
                    System.out.println("정답입니다.");

                    solved++;
                    if (solved == 5) {
                        break;
                    }
                    int temp = secondNum;
                    secondNum = firstNum;
                    firstNum = temp;
                }
                if (secondScannedAnswer != 1) {
                    System.out.println("오답입니다.");
                    wrongCount++;
                    if (wrongCount == 3) {
                        System.out.println("정답은 1번 입니다.");
                        wrongCount = 0;
                        solved = 0;
                    }
                }
            }

            if (firstNum < secondNum) {
                if (secondScannedAnswer == 2) {
                    System.out.println("정답입니다.");

                    solved++;
                    if (solved == 5) {
                        break;
                    }
                    int temp = secondNum;
                    secondNum = firstNum;
                    firstNum = temp;
                }
                if (secondScannedAnswer != 2) {
                    System.out.println("오답입니다.");
                    wrongCount++;
                    if (wrongCount == 3) {
                        System.out.println("정답은 2번 입니다.");
                        solved = 0;
                        wrongCount = 0;
                    }
                }
            }

        }
    }
}
