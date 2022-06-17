package week2.assignment.money.console;

import week2.assignment.money.domain.Money;

public class MoneyConsole {
    public void printWhenNotEnoughMoney(Money money, int bill, int leftMoney) {
        System.out.println("남은 용돈 "
                        + money.checkMyMoney()
                        + "원 중 비용이 "
                        + bill
                        + "원이며 총 "
                        + Math.abs(leftMoney) + "원이 부족합니다.");
    }

    public void printWhenEnoughMoney(Money money, int bill, int leftMoney) {
        System.out.println("남은 용돈 "
                        + money.checkMyMoney()
                        + "원 중 비용이 "
                        + bill
                        + "원이 청구되어 "
                        + leftMoney
                        + "원이 남았습니다.");
    }

    public void printWhenAddMoreMoney(Money money, int moreMoney, int totalMoney) {
        System.out.println("남은 용돈 "
                + money.checkMyMoney()
                + "원 중 "
                + moreMoney
                + "원이 추가되어 "
                + totalMoney
                + "원이 있습니다.");

    }

    public void printWhenScannerMoreMoney() {
        System.out.print("\n추가할 용돈을 입력해주세요 : ");
    }

    public void printAlreadyGetAddtionalMoney() {
        System.out.println("이미 추가적인 용돈을 받았습니다.");
    }

    public void printShutDownByNotEnoughMoney() {
        System.out.println("돈이 부족하여 주인장에게 쫓겨납니다.");
    }
}
