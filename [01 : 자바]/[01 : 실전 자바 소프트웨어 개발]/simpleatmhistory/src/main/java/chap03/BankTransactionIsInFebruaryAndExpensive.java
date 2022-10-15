package chap03;

import chap02.BankTransaction;
import chap03.BankTransactionFilter;

import java.time.Month;

/**
 * 예제 3-6
 * BankTransactionFilter를 구현하는 클래스
 */
public class BankTransactionIsInFebruaryAndExpensive implements BankTransactionFilter {

    @Override
    public boolean test(final BankTransaction bankTransaction) {
        return (bankTransaction.getDate().getMonth() == Month.FEBRUARY
                && bankTransaction.getAmount() >= 1_000);
    }
}
