package ch.welld.kie;

import ch.welld.kie.format.KnowledgeFormatConverter;
import ch.welld.kie.format.KnowledgeFormatConverterFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class KnowledgeFormatConverterFactoryTest {

    @Test
    public void testDrlConverterIsFound() {
        Optional<KnowledgeFormatConverter> converter = KnowledgeFormatConverterFactory.getConverter(".drl");
        Assert.assertNotNull(converter.orElseGet(() -> null));
    }

    @Test
    public void testTemplateConverterIsFound() {
        Optional<KnowledgeFormatConverter> converter = KnowledgeFormatConverterFactory.getConverter(".template");
        Assert.assertNotNull(converter.orElseGet(() -> null));
    }

    @Test
    public void testGdstConverterIsFound() {
        Optional<KnowledgeFormatConverter> converter = KnowledgeFormatConverterFactory.getConverter(".template");
        Assert.assertNotNull(converter.orElseGet(() -> null));
    }

    @Test
    public void testUknownConverterIsNotFound() {
        Optional<KnowledgeFormatConverter> converter = KnowledgeFormatConverterFactory.getConverter(".unknown");
        Assert.assertNull(converter.orElseGet(() -> null));
    }

}
