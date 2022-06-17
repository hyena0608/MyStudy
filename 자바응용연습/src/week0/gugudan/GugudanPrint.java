package week0.gugudan;

import java.util.Queue;

public class GugudanPrint {

    public static void print(Queue<Multiplication> operandQueue) {

        while (!operandQueue.isEmpty()) {
            System.out.println(operandQueue.poll().toString());
        }
    }

}
