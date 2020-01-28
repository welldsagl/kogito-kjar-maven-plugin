package ch.welld.kie.format;

import java.nio.file.Path;

public class UnsupportedKnowledgeFormatException extends Exception {

    private final Path unsupportedFilePath;

    public UnsupportedKnowledgeFormatException(Path unsupportedFilePath) {
        super();
        this.unsupportedFilePath = unsupportedFilePath;
    }

    public Path getUnsupportedFilePath() {
        return unsupportedFilePath;
    }
}
