package week0.gugudan.way;

import week0.gugudan.Multiplication;

import java.util.LinkedList;
import java.util.Queue;

public class GugudanByWhile implements Gugudan {


    @Override
    public Queue<Multiplication> createGugudan(int num) {

        Queue<Multiplication> multiplicationQueue = new LinkedList<>();

        int numberOne = 1;
        int numberTwo = 1;

        while (numberOne <= num) {
            while (numberTwo <= num) {
                multiplicationQueue.add(new Multiplication(numberOne, numberTwo++));
            }
            numberOne++;
            numberTwo = num;
        }

        return multiplicationQueue;
    }
}
