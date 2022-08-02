package week6.lecture;

import java.util.Scanner;

public class Main3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("N값을 입력해주세요 : ");
        int n = Integer.parseInt(sc.nextLine());

        // 피라미드 숫자 총 개수
        int piramidTotalCount = 0;
        for (int i = 1; i <= n; i++) {
            piramidTotalCount += i * 2 - 1;
        }
        //
        // 마지막 라인 (맨 마지막 " " 제거)
        int finalLineLength = -1;
        // 마지막 라인 숫자 개수
        int finalLineNumCount = (n * 2) - 1;
        // 마지막 라인 길이가 어때
        for (int i = piramidTotalCount; i >= piramidTotalCount - finalLineNumCount ; i--) {
            // 몇 자리수로 나오는지?
            finalLineLength += String.valueOf(((i * 2) - 1)).length() + 1;
        }

        int lineCount = 1;
        for (int i = 1; i <= n ; i++) {
            // 내보낼 문자열 초기화
            String str = "";
            // n번째 라인 숫자 개수
            int currentLineCount = (i * 2) - 1;
            // n번째 라인 길이 (가장 아래 자릿수 - (n번째 줄 길이 구하기) / 2) * " "
            for (int j = lineCount; j <= lineCount + currentLineCount; j++) {
                lineCount = j;
                str += ((j) * 2 - 1) + " ";
            }

            // + n번째 줄 숫자 대입
            // + \n

            System.out.println(str);
        }
    }
}
