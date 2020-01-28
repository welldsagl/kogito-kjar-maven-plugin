package ch.welld.kie;

import ch.welld.kie.format.KnowledgeFormatConverter;
import ch.welld.kie.format.KnowledgeFormatConverterFactory;
import ch.welld.kie.format.UnsupportedKnowledgeFormatException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class DroolsConverter {

    public static Set<String> getSupportedFormats() {
        return KnowledgeFormatConverterFactory.getSupportedFormats();
    }

    /**
     * Copies knowledge source file to a given directory.
     *
     * @param sourceFile the source file to copy. If it is a .gdst file, it is also converted to .drl
     * @param destinationPath the destination path to which the file is written
     * @param overwriteFile if true, overwrites the existing file
     * @return the created file
     * @throws IOException if there is an IO error while reading/writing files
     * @throws UnsupportedKnowledgeFormatException when trying to convert from an unsupported format to DRL
     *
     *     FIXME: if the knowledge contains both `rules.drl` and `rules.dgst` the behaviour could be non-deterministic
     *         (depending on which one gets copied first) and the value of {@param overwriteFile}
     */
    public static File convertKnowledgeFile(File sourceFile, Path destinationPath, boolean overwriteFile)
            throws IOException, UnsupportedKnowledgeFormatException {
        String extension = sourceFile.getName().substring(sourceFile.getName().lastIndexOf('.'));
        KnowledgeFormatConverter converter = KnowledgeFormatConverterFactory
                .getConverter(extension)
                .orElseThrow(() -> new UnsupportedKnowledgeFormatException(sourceFile.toPath()));
        String drlString = converter.convertToDrl(sourceFile);
        String fileName = sourceFile.getName().replaceAll(
            String.format("%s$", converter.supportedFormat()),
            ".drl"
        );
        return FileUtils.createFile(drlString, fileName, destinationPath, overwriteFile);
    }
}
