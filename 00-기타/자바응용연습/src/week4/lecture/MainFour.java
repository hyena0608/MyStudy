package week4.lecture;

public class MainFour {
    public static void main(String[] args) {

        int i = 0;
        int b = 0;
        int c = 0;

        for (; i < 100; i++) {
        }
        System.out.println("i = " + i);
        for (; b < i + 50; b++) {
        }
        System.out.println("b = " + b);
        for (; c < b * 2; c++) {
        }
        System.out.println("c = " + c);

        for (; c > -500; c--) {
        }
        System.out.println("c = " + c);

        for (; ; ) {
            if (i > -500) {
                i--;
            }
            if (b > -500) {
                b--;
            }
            if (i == -500 && b == -500) {
                break;
            }
        }
        System.out.println("i = " + i);
        System.out.println("b = " + b);

        if (i == b && b == c) {
            for (; i < 3000; i++) {
                if (i == 1500) {
                    System.out.println("i = " + i);
                    for (; b < 5000; b++) {
                        if (b > i + 1000) {
                            c++;
                        }
                    }
                }
            }
            System.out.println("i = " + i);
            System.out.println("b = " + b);
            for (; ; ) {
                if (i < b) {
                    i++;
                }
                if (c < b) {
                    c++;
                }
                if (i == b && c == b) {
                    System.out.println("i = " + i);
                    System.out.println("c = " + c);
                    break;
                }
            }
        }

    }
}
