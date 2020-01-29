package ch.welld.kie;

import ch.welld.kie.format.UnsupportedKnowledgeFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ch.welld.kie.DroolsConverter.convertKnowledgeFile;

public class DroolsConverterTest {

    private static final Path basePath = Paths.get("target/test-classes/fileutils");
    private static final Path outputPath = Paths.get("target/test-classes/fileutils-target");

    @Test
    public void testGdstToDrlConversion() {
        try {
            File sourceFile = basePath.resolve("innerDirectory/GdsSample2.gdst").toFile();
            File drlFile = convertKnowledgeFile(sourceFile, outputPath.resolve("innerDirectory"), true);

            Assert.assertNotNull(drlFile);
            Assert.assertTrue(drlFile.exists());
        } catch (IOException | UnsupportedKnowledgeFormatException e) {
            Assert.fail();
        }
    }

    @Test
    public void testGuidedTemplateToDrlConversion() {
        try {
            File sourceFile = basePath.resolve("innerDirectory/GuidedTemplateSample.template").toFile();
            File drlFile = convertKnowledgeFile(sourceFile, outputPath.resolve("innerDirectory"), true);

            Assert.assertNotNull(drlFile);
            Assert.assertTrue(drlFile.exists());
        } catch (IOException | UnsupportedKnowledgeFormatException e) {
            Assert.fail();
        }
    }

    @Test
    public void testUnsupportedFormatConversion() {
        File sourceFile = basePath.resolve("innerDirectory/Sample.unknown").toFile();
        try {
            File drlFile = convertKnowledgeFile(sourceFile, outputPath.resolve("innerDirectory"), true);

            // This should never be reached
            Assert.fail();
        } catch (UnsupportedKnowledgeFormatException e) {
            Assert.assertEquals(e.getUnsupportedFile().toPath(), sourceFile.toPath());
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testDrlCopy() {
        try {
            File sourceFile = basePath.resolve("innerDirectory/DrlSample.drl").toFile();
            File targetFile = convertKnowledgeFile(sourceFile, outputPath.resolve("innerDirectory"), true);

            Assert.assertNotNull(targetFile);
            Assert.assertTrue(targetFile.exists());
            Assert.assertEquals("target/test-classes/fileutils-target/innerDirectory/DrlSample.drl", targetFile.getPath());
        } catch (IOException | UnsupportedKnowledgeFormatException e) {
            Assert.fail();
        }
    }
}
