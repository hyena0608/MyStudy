package chap01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 예제 2-1 모든 거래 내역의 합 계산하기
 */
public class BankTransactionAnalyzerSimple {

    private static final String RESOURCES = "src/main/resources/";

    public static void main(final String[] args) throws IOException {

        final Path path = Paths.get(RESOURCES + "bank-data-simple.csv");
        final List<String> lines = Files.readAllLines(path);
        double total = 0d;
        for (final String line : lines) {
            final String[] columns = line.split(",");
            final double amount = Double.parseDouble(columns[1]);
            total += amount;
        }

        System.out.println("The total for all transactions is = " + total);
    }
}
