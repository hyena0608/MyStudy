package week0;

import java.util.Scanner;

public class GugudanApplication {
    public static void main(String[] args) {
        /**
         * 규민과 철수와 민영이 있다.
         * 	셋의 집은 각각 10km마다 떨어져 있는 거리에 존재한다.
         * 	세 명의 집을 차례대로 방문하려면 몇 km를 걸어가야 하는가.
         * 	답 :
         *
         * 	정답일 경우
         * 		정답입니다.
         *
         * 	오답일 경우
         * 		오답입니다.
         * 		답은 30km입니다.
         */

        Scanner sc = new Scanner(System.in);


        System.out.println("첫번째 사람의 이름을 입력해주세요.");
        String nameOne = sc.nextLine();
        System.out.println("두번째 사람의 이름을 입력해주세요.");
        String nameTwo = sc.nextLine();
        System.out.println("세번째 사람의 이름을 입력해주세요.");
        String nameThree = sc.nextLine();

        System.out.println("각 집의 km 거리를 입력해주세요");
        int km = sc.nextInt();
        System.out.println("정답을 입력해주세요.");
        int inputAnswer = sc.nextInt();

        int answer = 3 * km;

        System.out.println(nameOne + "과 " + nameTwo + "와" + nameThree + "이 있다");
        System.out.println("셋의 집은 각각 " + km + "km마다 떨어져 있는 거리에 존재한다.");
        System.out.println("세 명의 집을 차례대로 방문하려면 몇 km를 걸어가야 하는가.");
        System.out.println("답:");
        System.out.println();
        if (inputAnswer == answer) {
            System.out.println("정답입니다.");
        } else if (inputAnswer != answer) {
            System.out.println("오답입니다.");
            System.out.println("답은" + answer + "km입니다.");
       }
    }
}
