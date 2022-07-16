package week6.lecture;

import java.util.Scanner;

public class Main2 {

    // 수학적 접근이 아닌 알고리즘으로 풀어오기
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("N값을 입력해주세요 : ");
        int n = Integer.parseInt(sc.nextLine());

        // 맨 아래 층 길이
        int c =  n * n;
        int lastSpaceCount = 0 - 1;
        for (int i = (n - 1) * (n - 1) + 1; i <= c; i++) {
            String num = String.valueOf(((i * 2) - 1));
            lastSpaceCount += num.length() + 1;
        }

        int totalCountWithOutN = 0;
        for (int i = 1; i <= n; i++) {

            // 현재 층 길이
            int spaceCount = 0 - 1;
            for (int j = (i - 1) * (i - 1) + 1; j <= i * i; j++) {
                String num = String.valueOf(((j * 2) - 1));
                spaceCount += num.length() + 1;
            }

            // 현재 층 스페이스(space) 계산 [맨 아래 층과 현재 층 길이 비교]
            String piramid = "";
            for (int j = 1; j < lastSpaceCount / 2 - spaceCount / 2; j++) {
                piramid += " ";
            }

            // 현재 층 숫자 추가
            for (int num = totalCountWithOutN * 2 + 1; num <= (totalCountWithOutN + 2 * i - 1) * 2 - 1; num += 2) {
                piramid += num + " ";
            }

            // 현재 층 출력
            System.out.println(piramid);

            // 현재까지 쌓인 층 누적
            totalCountWithOutN += 2 * i - 1;
        }

        int right = 2 * (totalCountWithOutN) - 1;
        int nCount = 2 * n - 1;
        int left = 2 * (totalCountWithOutN - nCount + 1) - 1;

        System.out.println(n + "층의 왼쪽 : " + left);
        System.out.println(n + "층의 오른 : " + right);
    }
}
