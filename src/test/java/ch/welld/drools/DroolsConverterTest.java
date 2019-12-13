package ch.welld.drools;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ch.welld.drools.DroolsConverter.copyKnowledge;

public class DroolsConverterTest {

    private static final Path basePath = Paths.get("target/test-classes/fileutils");
    private static final Path outputPath = Paths.get("target/test-classes/fileutils-target");

    @Test
    public void testGdstToDrlConversion() {
        try {
            File sourceFile = basePath.resolve("innerDirectory/GdsSample2.gdst").toFile();
            File drlFile = copyKnowledge(sourceFile, outputPath.resolve("innerDirectory"), true);

            Assert.assertNotNull(drlFile);
            Assert.assertTrue(drlFile.exists());
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testDrlCopy() {
        try {
            File sourceFile = basePath.resolve("innerDirectory/DrlSample.drl").toFile();
            File targetFile = copyKnowledge(sourceFile, outputPath.resolve("innerDirectory"), true);

            Assert.assertNotNull(targetFile);
            Assert.assertTrue(targetFile.exists());
            Assert.assertEquals("target/test-classes/fileutils-target/innerDirectory/DrlSample.drl", targetFile.getPath());
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
