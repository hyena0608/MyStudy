package week5.lecture;

public class Main_1 {
    public static void main(String[] args) {

        for (int i = 2; i <= 40; i += 2) {

            if (i != 4 || i != 8 || i != 14) {
                int dansu = i;
                if ((dansu / 10) * 10 == i) {
                    dansu = i + 1;
                }

                if (i == 18) {
                    dansu = 118;
                }
                if (i == 20) {
                    dansu = 220;
                }

                System.out.println("=========구구단 " + (dansu) + "단=====");
                for (int k = 1; k <= 9; k++) {
                    System.out.println(dansu + " x " + k + " = " + (dansu * k));
                }
            }

        }

    }
}
