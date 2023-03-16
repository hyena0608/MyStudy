package item20;

import org.junit.jupiter.api.Test;

public class InterfaceTest {


}

interface Interface_ {

    // === 클래스 상수 필드 ===
    public static final int publicStaticFinalField = 0;

    // === static 메서드 ===
    public static void publicStaticMethod() {
    }

    private static void privateStaticMethod() {
    }

    // === JDK 8 : default 메서드 구현 ===
    public default void publicDefaultMethod() {
    }

    // === JDK 9 : private 메서드 구현 ===
    private void privateMethod() {
    }

    // === 추상 메서드 ===
    public abstract void abstractMethod();
}

class Dealer implements Gambler {
    @Override
    public void bet() {
        System.out.println("배팅하다.");
    }

    @Override
    public void hit() {
        System.out.println("딜러의 규칙대로 히트하다.");
    }

    @Override
    public void bust() {
        System.out.println("버스트 되다.");
    }
}

interface Gambler {
    void bet();
    void hit();
    void bust();
}

// 추상 골격 구현 클래스 - 중복된 메서드르 제거한다.
abstract class AbstractGambler implements Gambler {
    // 같은 동작을 하는 메서드 bet
    @Override
    public void bet() {
        System.out.println("배팅하다.");
    }

    // 같은 동작을 하는 메서드 bust
    @Override
    public void bust() {
        System.out.println("버스트 되다.");
    }
}

class Player extends AbstractGambler implements Gambler {
    @Override
    public void hit() {
        System.out.println("플레이어의 규칙대로 히트하다.");
    }
}
