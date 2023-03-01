package designpattern.strategy;

public class BackDoong extends Person {

    public BackDoong() {
        bikeStrategy = new BicycleNoSkill();
    }
}

class Prong extends Person {

    public Prong() {
        bikeStrategy = new BicycleSkill();
    }
}

abstract class Person {
    public BikeStrategy bikeStrategy;

    void rideBicycle() {
        bikeStrategy.rideBicycle();
    }
}

interface BikeStrategy {
    void rideBicycle();
}

class BicycleSkill implements BikeStrategy {

    @Override
    public void rideBicycle() {
        System.out.println("자전거를 탑니다.");
    }
}

class BicycleNoSkill implements BikeStrategy {

    @Override
    public void rideBicycle() {
        System.out.println("저.. 사실 자전거 못타요.");
    }
}

class Main {
    public static void main(String[] args) {
        BackDoong backDoong = new BackDoong();
        Prong prong = new Prong();

        backDoong.rideBicycle();
        prong.rideBicycle();
    }
}