package ch.welld.drools;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ch.welld.drools.FileUtils.*;

public class FileUtilsTest {

    @Test
    public void testCreateDirectoryIfNotExists() throws IOException {
        // Cleanup
        Path basePath = Paths.get("target/test-classes/does-not-exist");
        Path nestedPath = basePath.resolve("a");
        Files.deleteIfExists(nestedPath);
        Files.deleteIfExists(basePath);

        createDirectoryIfNotExists(nestedPath);
        Assert.assertTrue(nestedPath.toFile().exists());
    }

    @Test
    public void testGetAllGdstFiles() throws IOException {
        File directory = new File( "target/test-classes/fileutils" );
        List<Path> gdstFiles = findAllFilesWithExtensions(directory, Set.of(".gdst"));
        Assert.assertEquals(2, gdstFiles.size());
        List<String> fileNames = gdstFiles.stream().map(Path::toString).collect(Collectors.toList());
        Assert.assertTrue(fileNames.contains("target/test-classes/fileutils/GdsSample.gdst"));
        Assert.assertTrue(fileNames.contains("target/test-classes/fileutils/innerDirectory/GdsSample2.gdst"));
    }
}
