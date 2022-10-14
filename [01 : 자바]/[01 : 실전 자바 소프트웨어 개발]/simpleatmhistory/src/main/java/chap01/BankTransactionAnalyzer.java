package chap01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * 예제 2-5
 * - 입출금 내역 CSV 파서 이용하기
 * 예제 2-6
 * - 입출금 내역 목록 처리
 * 예제 2-8
 *  - BankStatementProcessor 클래스를 이용해 입출금 내역 목록 처리
 */
public class BankTransactionAnalyzer {

    private static final String RESOURCES = "src/main/resources/";
    private static final BankStatementCSVParser bankStatementParse = new BankStatementCSVParser();

    public static void main(final String[] args) throws IOException {

        final String fileName = args[0];
        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementParse.parseLinesFromCSV(lines);
        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(bankTransactions);

        collectSummary(bankStatementProcessor);

    }

    private static void collectSummary(final  BankStatementProcessor bankStatementProcessor) {
        System.out.println("The total for all transactions is = "
                + bankStatementProcessor.calculateTotalAmount());

        System.out.println("The total for all transactions in January is = "
                + bankStatementProcessor.calculateToTotalInMonth(Month.JANUARY));

        System.out.println("The total for all transactions in February is = "
                + bankStatementProcessor.calculateToTotalInMonth(Month.FEBRUARY));

        System.out.println("The total for all transactions in January is = "
                + bankStatementProcessor.calculateTotalForCategory("Salary"));
    }

    public static double calculateTotalAmount(final List<BankTransaction> bankTransactions) {
        double total = 0d;
        for (final BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }

    public static List<BankTransaction> selectInMonth(final List<BankTransaction> bankTransactions,
                                                      final Month month) {
        final List<BankTransaction> bankTransactionsInMonth = new ArrayList<>();
        for (final BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                bankTransactionsInMonth.add(bankTransaction);
            }
        }
        return bankTransactionsInMonth;
    }
}
