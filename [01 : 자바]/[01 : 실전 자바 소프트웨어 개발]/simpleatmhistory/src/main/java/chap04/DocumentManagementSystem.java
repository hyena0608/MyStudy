package chap04;

import java.util.HashMap;
import java.util.Map;

/**
 * 예제 4-6
 * - 임포터 등록
 * 예제 4-7
 * - importFile 정의
 */
public class DocumentManagementSystem {

    private final Map<String, Importer> extensionToImporter = new HashMap<>();

    public DocumentManagementSystem() {
        extensionToImporter.put("letter", new LetterImporter());
        extensionToImporter.put("report", new ReportImporter());
        extensionToImporter.put("jpg", new ImageImporter());
    }
}
