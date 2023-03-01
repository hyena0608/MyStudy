package week1;

import java.util.Scanner;

public class Two {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("a를 입력해주세요.");
        int a = sc.nextInt();

        System.out.println("b를 입력해주세요.");
        int b = sc.nextInt();

        System.out.println("c를 입력해주세요.");
        int c = sc.nextInt();
        int compareC = 0;
        int originalC = 0;

        System.out.println("d를 입력해주세요.");
        int d = sc.nextInt();
        int compareD = 0;
        int originalD = 0;

        System.out.println("e를 입력해주세요.");
        int e = sc.nextInt();
        int compareE = 0;
        int originalE = 0;

        System.out.println("f를 입력해주세요.");
        int f = sc.nextInt();
        int compareF = 0;
        int originalF = 0;

        System.out.println("g를 입력해주세요.");
        int g = sc.nextInt();
        int compareG = 0;
        int originalG = 0;

        String answer =  "";

        if (b >= 4000) {
            if (b > a) {
                compareC = a + b;
                originalC = compareC;
            }
            if (b < a) {
                compareC = a + b - 300;
                originalC = compareC;
            }
        }

//        compareD = a + b + c;
        originalD = a + b + originalC;

//        compareE = (int) (a + b + c) / 3;
        originalE = (int) (a + b + originalC) / 3;

//        compareF = a + b + c + d + e + 5000;
        originalF = a + b + originalC + originalD + originalE + 5000;

//        compareG = f * 2;
        originalG = originalF * 2;

        if ((a < 5000 && a > 10000) && (b < 4000) && originalC != c && originalD != d && originalE != e && originalF != f && originalG != g) {
            System.out.print("모두 잘못 되었습니다.");
        }

        if ((a >= 5000 && a <= 10000) || (b >= 4000) || originalC == c || originalD == d || originalE == e || originalF == f || originalG == g) {
            if (a >= 5000 && a <= 10000) {
                System.out.print("a가 ");
            }
            if (b >= 4000) {
                System.out.print("b가 ");
            }
            if (c != originalC) {
                System.out.print("c가 ");
            }
            if (d != originalD) {
                System.out.print("d가 ");
            }
            if (e != originalE) {
                System.out.println("e가 ");
            }
            if (g != originalG) {
                System.out.println("g가 ");
            }
            System.out.println("잘못되었습니다.");
        }

    }
}
