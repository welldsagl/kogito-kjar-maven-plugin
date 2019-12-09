package ch.welld.drools;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileUtils {

    public static void createDirectoryIfNotExists(File directory) {
        if ( !directory.exists() ) {
            directory.mkdirs();
        }
    }

    public static List<File> getAllFilesWithExtension(File directory, List<String> extensions) {
        return Arrays.stream(directory.listFiles())
                .flatMap(file -> {
                    if (file.isFile() && extensions.stream().anyMatch(ext -> file.getName().endsWith(ext))) {
                        return Stream.of(file);
                    }
                    if (file.isDirectory()) {
                        return getAllFilesWithExtension(file, extensions).stream();
                    }
                    return Stream.of();
                })
                .collect(Collectors.toList());
    }

    public static String getRelativePath(String fullPath, String basePath) {
        return new File(basePath)
                .toURI()
                .relativize(
                    new File(fullPath)
                        .getParentFile()
                        .toURI()
                )
                .getPath();
    }

    public static File copyFile(File source, File targetDirectory, boolean overrideFile) {
        try {
            File copy = new File(targetDirectory, source.getName());
            createDirectoryIfNotExists(targetDirectory);
            Files.copy(source.toPath(), copy.toPath(), overrideFile ? REPLACE_EXISTING : null);
            return copy;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
