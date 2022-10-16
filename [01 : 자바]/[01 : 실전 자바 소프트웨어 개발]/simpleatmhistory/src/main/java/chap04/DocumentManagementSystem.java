package chap04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 예제 4-6
 * - 임포터 등록
 * 예제 4-7
 * - importFile 정의
 *  - 검증 메서드
 */
public class DocumentManagementSystem {

    private final List<Document> documents = new ArrayList<>();
    private final List<Document> documentsView = Collections.unmodifiableList(documents);
    private final Map<String, Importer> extensionToImporter = new HashMap<>();

    public DocumentManagementSystem() {
        extensionToImporter.put("letter", new LetterImporter());
        extensionToImporter.put("report", new ReportImporter());
        extensionToImporter.put("jpg", new ImageImporter());
    }

    public void importFile(final String path) throws IOException {
        final File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }

        final int separatorIndex = path.lastIndexOf('.');
        if (separatorIndex != -1) {
            if (separatorIndex == path.length()) {
                throw new UnknownFileTypeException("No extension found For file: " + path);
            }
            final String extension = path.substring(separatorIndex + 1);
            final Importer importer = extensionToImporter.get(extension);
            if (importer == null) {
                throw new UnknownFileTypeException("for File: " + path);
            }

            final Document document = importer.importFile(file);
            documents.add(document);
        } else {
            throw new UnknownFileTypeException("No extension found For file: " + path);
        }
    }

    public List<Document> contents() {
        return documentsView;
    }

    public List<Document> search(final String query) {
        return documents.stream()
                .filter(Query.parse(query))
                .collect(Collectors.toList());
    }
}
