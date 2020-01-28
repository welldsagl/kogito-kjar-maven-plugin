package ch.welld.kie.format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A {@link KnowledgeFormatConverter} for DRL files.
 */
public class DrlFormatConverter implements KnowledgeFormatConverter {

    /**
     * Returns the .drl file content as is.
     *
     * @param sourceFile the source file
     * @return the created file
     *
     * @throws IOException when I/O errors occur
     */
    @Override
    public String convertToDrl(File sourceFile) throws IOException {
        return Files.readString(sourceFile.toPath());
    }

    @Override
    public String supportedFormat() {
        return ".drl";
    }
}
