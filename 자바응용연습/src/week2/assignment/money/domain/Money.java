package week2.assignment.money.domain;

public interface Money {

    abstract int checkMyMoney();

    abstract void setMyMoney(int money);

    abstract boolean isAlreadyGetAddtionalMoney();

    abstract void setAddtionalMoneyTrue();

//    abstract int spendMoney(int bill);
//
//    abstract int earnMoney(int money);
}
