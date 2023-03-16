package npe;

import org.junit.jupiter.api.Test;

public class NpeTest {

    @Test
    void 널_포인트_예외는_caught_되지_않는다() {
//        try {
            processNPE();
//        } catch (NullPointerException e) {
//            System.out.println();
//        }
    }

    void processNPE() {
        throw new NullPointerException();
    }
}
