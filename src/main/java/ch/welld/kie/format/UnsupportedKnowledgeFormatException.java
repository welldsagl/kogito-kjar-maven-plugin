package ch.welld.kie.format;

import java.io.File;

public class UnsupportedKnowledgeFormatException extends Exception {

    private final File unsupportedFile;

    public UnsupportedKnowledgeFormatException(File unsupportedFile) {
        super();
        this.unsupportedFile = unsupportedFile;
    }

    public File getUnsupportedFile() {
        return unsupportedFile;
    }
}
