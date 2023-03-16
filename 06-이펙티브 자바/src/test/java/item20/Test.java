package item20;

public class Test {


}

class AbstractImpl extends AbstractExample {

    @Override
    void printAbstract() {

    }
}

class InterfaceImpl implements interfaceExample {

    @Override
    public void printAbstract() {

    }
}

abstract class AbstractExample {

    // 추상 메서드
    abstract void printAbstract();
}

interface interfaceExample {

    // 추상 메서드
    void printAbstract();
}