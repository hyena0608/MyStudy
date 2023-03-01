package chap03;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * OCP
 * - 검색 관련 클래스를 따로 작성하기보다 현 클래스 내부에 메서드로 검색 기능을 작성하는게 낫다.
 * - WHY ?
 * - 클래스명을 보면 Processor 로 처리 담당을 하고 있다고 판단할 수 있다.
 * - 때문에 ~Finder 클래스를 작성한다고 했을 때 실제 클래스들이 어떤 관계로 이루어져있는지 판단하기 어려워질 수 있다.
 * - 그리고 검색 기능은 일종의 처리 기능으로 현재 클래스에 맞다고 생각한다.
 * <p>
 * 예제 3-1
 * - 특정 금액 이상의 은행 거래 내역 찾기
 * 예제 3-2
 * - 특정 월의 입출금 내역 찾기
 * 예제 3-3
 * - 특정 월이나 금액으로 입출금 내역 검색
 * <p>
 * 문제점 - 예제 3-1, 3-2, 3-3
 * - 검색 속성 조합시 코드가 복잡해진다.
 * - 반복 로직과 비즈니스 로직이 결합되어 분리하기 어렵다.
 * - 코드 반복
 * <p>
 * 예제 3-5
 * - OCP를 적용한 후 유연해진 findTransactions() 메서드
 * <p>
 * 예제 3-11
 * BankTransactionProcessor 클래스의 핵심 연산
 */
@FunctionalInterface
interface BankTransactionSummarizer {
    double summarize(double accumulator, BankTransaction bankTransaction);
}

@FunctionalInterface
interface BankTransactionFilter {
    boolean test(BankTransaction bankTransaction);
}

public class BankStatementProcessor {


    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(final List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double summarizeTransactions(final BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction);
        }
        return result;
    }

    public double calculateToTotalInMonth(final Month month) {
        return summarizeTransactions((acc, bankTransactions) ->
                bankTransactions.getDate().getMonth() == month ? acc + bankTransactions.getAmount() : acc
        );
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

    public List<BankTransaction> findTransactions(final BankTransactionFilter bankTransactionFilter) {
        final List<BankTransaction> result = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransactionFilter.test(bankTransaction)) {
                result.add(bankTransaction);
            }
        }
        return result;
    }

    public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
        return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
    }
}
