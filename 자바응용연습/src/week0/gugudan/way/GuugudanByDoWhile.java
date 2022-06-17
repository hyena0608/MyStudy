package week0.gugudan.way;

import week0.gugudan.Multiplication;

import java.util.LinkedList;
import java.util.Queue;

public class GuugudanByDoWhile implements Gugudan {
    @Override
    public Queue<Multiplication> createGugudan(int num) {

        Queue<Multiplication> multiplicationQueue = new LinkedList<>();

        int numberOne = 1;
        int numberTwo = 1;

        do {
            do {
                multiplicationQueue.add(new Multiplication(numberOne, numberTwo++));
            } while (numberTwo <= num);
            numberTwo = 1;
            numberOne++;
        } while (numberOne <= num);

        return multiplicationQueue;
    }
}
