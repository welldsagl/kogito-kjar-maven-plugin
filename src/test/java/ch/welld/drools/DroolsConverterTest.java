package ch.welld.drools;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static ch.welld.drools.DroolsConverter.copyKnowledge;

public class DroolsConverterTest {

    private static final String basePath = "target/test-classes/fileutils";
    private static final File outputDirectory = new File("target/test-classes/fileutils-target");

    @Test
    public void testGdstToDrlConversion() {
        try {
            File sourceFile = new File("target/test-classes/fileutils/innerDirectory/GdsSample2.gdst");
            File drlFile = copyKnowledge(sourceFile, outputDirectory, basePath, true);

            Assert.assertNotNull(drlFile);
            Assert.assertTrue(drlFile.exists());
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testDrlCopy() {
        try {
            File sourceFile = new File("target/test-classes/fileutils/innerDirectory/DrlSample.drl");
            File targetFile = copyKnowledge(sourceFile, outputDirectory, basePath, true);

            Assert.assertNotNull(targetFile);
            Assert.assertTrue(targetFile.exists());
            Assert.assertEquals("target/test-classes/fileutils-target/innerDirectory/DrlSample.drl", targetFile.getPath());
        } catch (IOException e) {
            Assert.fail();
        }
    }
}
