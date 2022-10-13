package week2.assignment.money.service;

import week2.assignment.money.domain.Money;
import week2.assignment.money.console.MoneyConsole;

import java.util.Scanner;

public class MoneyService {
    private final MoneyConsole moneyConsole = new MoneyConsole();

    public int spendMoney(Money money, int bill) {
        int leftMoney = money.checkMyMoney() - bill;

        if (leftMoney >= 0) {
            money.setMyMoney(leftMoney);
            moneyConsole.printWhenEnoughMoney(money, bill, leftMoney);
        } else if (leftMoney < 0 && money.isAlreadyGetAddtionalMoney()) {
            moneyConsole.printWhenNotEnoughMoney(money, bill, leftMoney);
            moneyConsole.printAlreadyGetAddtionalMoney();
            moneyConsole.printShutDownByNotEnoughMoney();
            System.exit(0);
        } else if (leftMoney < 0 && !money.isAlreadyGetAddtionalMoney()) {
            moneyConsole.printWhenNotEnoughMoney(money, bill, leftMoney);
            addMoreMoney(money);
            money.setAddtionalMoneyTrue();

            leftMoney = money.checkMyMoney() - bill;
            if (leftMoney < 0) {
                moneyConsole.printWhenNotEnoughMoney(money, bill, leftMoney);
                moneyConsole.printShutDownByNotEnoughMoney();
                System.exit(0);
            }
            moneyConsole.printWhenEnoughMoney(money, bill, leftMoney);
            money.setMyMoney(leftMoney);
        }

        return leftMoney;
    }

    public void addMoreMoney(Money money) {
        moneyConsole.printWhenScannerMoreMoney();

        int moreMoney = new Scanner(System.in).nextInt();
        int totalMoney = money.checkMyMoney() + moreMoney;

        moneyConsole.printWhenAddMoreMoney(money, moreMoney, totalMoney);

        money.setMyMoney(totalMoney);
    }

}
