package week2.assignment.money.domain;

public class PocketMoney implements Money{

    private int pockeyMoney;
    private boolean isAlreadyGetAddtionalMoeny = false;

    public PocketMoney(int pockeyMoney) {
        this.pockeyMoney = pockeyMoney;
    }

    @Override
    public int checkMyMoney() {
        return pockeyMoney;
    }

    @Override
    public void setMyMoney(int money) {
        pockeyMoney = money;
    }

    @Override
    public boolean isAlreadyGetAddtionalMoney() {
        return isAlreadyGetAddtionalMoeny;
    }

    @Override
    public void setAddtionalMoneyTrue() {
        isAlreadyGetAddtionalMoeny = true;
    }
}
