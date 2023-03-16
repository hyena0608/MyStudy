package item20;

public class AbstractTest {


}


abstract class Abstract_ {

    // === 클래스 상수 필드 ===
    public static final int publicStaticFinalField = 0;
    protected static final int protectedStaticFinalField = 0;
    static final int defaultStaticFinalField = 0;
    private static final int privateStaticFinalField = 0;


    // === 클래스 필드 ===
    public static int publicStaticField;
    protected static int protectedStaticField;
    static int defaultStaticField;
    private static int privateStaticField;


    // === 인스턴스 상수 필드 ===
    public final int publicFinalInstanceField;
    protected final int protectedFinalInstanceField;
    final int defaultFinalInstanceField;
    private final int privateFinalInstanceField;

    public Abstract_(int publicFinalInstanceField, int protectedFinalInstanceField, int defaultFinalInstanceField, int privateFinalInstanceField) {
        this.publicFinalInstanceField = publicFinalInstanceField;
        this.protectedFinalInstanceField = protectedFinalInstanceField;
        this.defaultFinalInstanceField = defaultFinalInstanceField;
        this.privateFinalInstanceField = privateFinalInstanceField;
    }


    // === 인스턴스 필드 ===
    public int publicInstanceField;
    protected int protectedInstanceField;
    int defaultInstanceField;
    private int privateInstanceField;


    // === 구현된 static 메서드 ===
    public static void publicStaticMethod() {
    }

    protected static void protectedStaticMethod() {
    }

    static void defaultStaticMethod() {
    }

    private static void privateStaticMethod() {
    }


    // === 구현된 메서드 ===
    public void publicMethod() {
    }

    protected void protectedMethod() {
    }

    void defaultMethod() {

    }

    private void privateMethod() {
    }


    // === 추상 메서드 ===
    public abstract void publicAbstractMethod();

    protected abstract void protectedAbstractMethod();

    abstract void defaultAbstractMethod();
}

interface ComparableInterface {
}

abstract class ComparableAbstract {
}

abstract class Number_ extends ComparableAbstract {

}
//class Dealer extends GamblerPerson {}
//class Player extends GamblerPerson {}
//
//// GamblerPerson -> 단일 상속이기 때문에 새로운 추상 클래스를 만든다.
//abstract class GamblerPerson {
//    Gambler gambler;
//    Person person;
//}
//
//abstract class Gambler {}
//abstract class Person {}