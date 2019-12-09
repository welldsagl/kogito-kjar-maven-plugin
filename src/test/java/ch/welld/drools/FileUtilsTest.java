package ch.welld.drools;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ch.welld.drools.FileUtils.getAllFilesWithExtension;
import static ch.welld.drools.FileUtils.getRelativePath;

public class FileUtilsTest {

    @Test
    public void testGetAllGdstFiles() {
        File directory = new File( "target/test-classes/fileutils" );
        List<File> gdstFiles = getAllFilesWithExtension(directory, Collections.singletonList(".gdst"));
        Assert.assertEquals(2, gdstFiles.size());
        List<String> fileNames = gdstFiles.stream().map(File::getName).collect(Collectors.toList());
        Assert.assertTrue(fileNames.contains("GdsSample.gdst"));
        Assert.assertTrue(fileNames.contains("GdsSample2.gdst"));
    }

    @Test
    public void testGetRelativePath() {
        String fullPath = "target/test-classes/fileutils/innerDirectory/GdsSample.gdst";
        String basePath = "target/test-classes/fileutils/";
        String relativePath = getRelativePath(fullPath, basePath);
        Assert.assertEquals("innerDirectory/", relativePath);
    }
}
