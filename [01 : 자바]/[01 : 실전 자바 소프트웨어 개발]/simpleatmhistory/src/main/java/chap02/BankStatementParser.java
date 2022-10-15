package chap02;

import chap01.BankTransaction;

import java.util.List;

public interface BankStatementParser {
    chap01.BankTransaction parseFrom(String line);
    List<BankTransaction> parseLinesFrom(List<String> lines);
}
