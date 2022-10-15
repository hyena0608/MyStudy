package chap02;

import chap01.BankTransaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

/**
 * 예제 2-12
 * 특정 파서와의 결합 제거
 */
public class BankStatementAnalyzer {

    private static final String RESOURCES = "src/main/resources/";

    public void analyze(final String fileName,
                        final BankStatementParser bankStatementParser) throws IOException {

        final Path path = Paths.get(RESOURCES + fileName);
        final List<String> lines = Files.readAllLines(path);

        final List<BankTransaction> bankTransactions = bankStatementParser.parseLinesFrom(lines);

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
}
