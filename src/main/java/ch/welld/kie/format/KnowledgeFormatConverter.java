package ch.welld.kie.format;

import java.io.File;
import java.io.IOException;

/**
 * An interface for a converter of drools knowledge format.
 */
public interface KnowledgeFormatConverter {

    /**
     * Converts the source format to DRL.
     *
     * @param sourceFile the file to convert
     * @return the equivalent rules in DRL format
     */
    String convertToDrl(File sourceFile) throws IOException;

    /**
     * Returns the file format supported by this converter.
     *
     * @return the file format supported by this converter
     */
    String supportedFormat();
}
