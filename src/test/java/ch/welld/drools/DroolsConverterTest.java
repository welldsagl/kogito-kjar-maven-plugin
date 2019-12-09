package ch.welld.drools;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static ch.welld.drools.DroolsConverter.convertGdst;

public class DroolsConverterTest {

    private static final String basePath = "target/test-classes/fileutils";
    private static final File outputDirectory = new File("target/test-classes/fileutils-target");
    private static final File sourceFile = new File("target/test-classes/fileutils/innerDirectory/GdsSample2.gdst");

    private File copyFile(File source) {
        try {
            File copy = new File(source.getAbsolutePath() + "copy.gdst");
            Files.copy(source.toPath(), copy.toPath());
            return copy;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testGdstToDrlConversion() {
        try {
            File drlFile = convertGdst(sourceFile, outputDirectory, basePath, false);

            Assert.assertNotNull(drlFile);
            Assert.assertTrue(drlFile.exists());
            Assert.assertTrue(sourceFile.exists());
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testGdstToDrlConversionWithDeletion() {
        try {
            File copy = copyFile(sourceFile);
            File drlFile = convertGdst(copy, outputDirectory, basePath, true);

            Assert.assertNotNull(drlFile);
            Assert.assertTrue(drlFile.exists());
            Assert.assertFalse(copy.exists());
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
