package ch.welld.kie.format;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A factory of {@link KnowledgeFormatConverter}.
 */
public class KnowledgeFormatConverterFactory {

    private KnowledgeFormatConverterFactory() {
        // prevent instantiation
    }

    private static List<KnowledgeFormatConverter> converters = List.of(
        new GdstFormatConverter(),
        new TemplateFormatConverter(),
        new DrlFormatConverter()
    );

    /**
     * Returns the set of supported file extensions.
     *
     * @return the set of supported file extensions
     */
    public static Set<String> getSupportedFormats() {
        return converters.stream().map(KnowledgeFormatConverter::supportedFormat).collect(Collectors.toSet());
    }

    /**
     * Returns a converter for a specific file format, when it exists.
     *
     * @param format the format for which we should return a converter
     * @return a converter for the format, when it exists
     */
    public static Optional<KnowledgeFormatConverter> getConverter(String format) {
        return converters
                .stream()
                .filter(c -> c.supportedFormat().equals(format))
                .findFirst();
    }
}
