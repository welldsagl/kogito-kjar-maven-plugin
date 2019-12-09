package ch.welld.drools;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static void createDirectoryIfNotExists(File directory) {
        if ( !directory.exists() ) {
            directory.mkdirs();
        }
    }

    public static List<File> getAllFilesWithExtension(File directory, String extension) {
        return Arrays.stream(directory.listFiles())
                .flatMap(file -> {
                    if (file.isFile() && file.getName().endsWith(extension)) {
                        return Stream.of(file);
                    }
                    if (file.isDirectory()) {
                        return getAllFilesWithExtension(file, extension).stream();
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
}
