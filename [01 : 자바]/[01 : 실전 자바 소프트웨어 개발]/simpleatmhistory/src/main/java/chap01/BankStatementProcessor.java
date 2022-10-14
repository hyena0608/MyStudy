package chap01;

import java.time.Month;
import java.util.List;

/**
 * 예제 2-7
 * 클래스의 계산 연산 그룹화
 *
 * 기존 BankStatementAnalyzer 클래스를 보면 응집도가 떨어진다.
 *  - 클래스 내 계산 작업, 파싱, 결과 전송이 관련이 없기 때문이다.
 *
 * 현재 클래스를 작성함으로서 계산 연산을 분리한다.
 */
public class BankStatementProcessor {

    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double calculateTotalAmount() {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public double calculateToTotalInMonth(final Month month) {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }

    public double calculateTotalForCategory(final String category) {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDescription().equals(category)) {
                total += bankTransaction.getAmount();
            }
        }
        return total;
    }
}
