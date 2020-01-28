package ch.welld.kie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    /**
     * Creates a directory if it does not exist.
     *
     * @param directoryPath the path to which the directory will be created
     */
    public static void createDirectoryIfNotExists(Path directoryPath) {
        File directory = directoryPath.toFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Finds all files matching the given set of extensions.
     *
     * @param directory the directory to search
     * @param extensions the set of valid extensions
     * @return a list of file with matching extensions
     */
    public static List<Path> findAllFilesWithExtensions(File directory, Set<String> extensions) throws IOException {
        try (Stream<Path> walk = Files.walk(directory.toPath())) {
            return walk
                    .filter(s -> extensions.stream().anyMatch(ext -> s.toString().endsWith(ext)))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Utility function to generate a drl file.
     *
     * @param content the content of the file
     * @param fileName the name of the file
     * @param destinationPath the destination path to which the file is written
     * @param overwriteFile if true, overwrites the existing file
     * @return the created file
     */
    public static File createFile(String content, String fileName, Path destinationPath, boolean overwriteFile)
            throws IOException {
        createDirectoryIfNotExists(destinationPath);
        File targetFile = new File(destinationPath.toFile(), fileName);
        if (overwriteFile || !targetFile.exists()) {
            Files.write(targetFile.toPath(), content.getBytes());
        }
        return targetFile;
    }
}
