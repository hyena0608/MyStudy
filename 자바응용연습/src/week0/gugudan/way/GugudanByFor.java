package week0.gugudan.way;

import week0.gugudan.Multiplication;

import java.util.LinkedList;
import java.util.Queue;

public class GugudanByFor implements Gugudan {

    @Override
    public Queue<Multiplication> createGugudan(int num) {

        Queue<Multiplication> multiplicationQueue = new LinkedList<>();

        for (int numberOne = 1; numberOne <= num; numberOne++) {
            for (int numberTwo = 1; numberTwo <= num; numberTwo++) {
                multiplicationQueue.add(new Multiplication(numberOne, numberTwo));
            }
        }

        return multiplicationQueue;
    }

}
