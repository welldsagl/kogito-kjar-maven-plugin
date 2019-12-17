package ch.welld.kie;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return Files.walk(directory.toPath())
            .filter(s -> extensions.stream().anyMatch(ext -> s.toString().endsWith(ext)))
            .collect(Collectors.toList());
    }

    /**
     * Copies a source file to the destination path, creating non existing directories along the path.
     *
     * @param source the file to copy
     * @param destinationPath the destination path
     * @param overwriteFile whether to replace an existing file with the same name
     * @return the copied file
     */
    public static File copyFile(File source, Path destinationPath, boolean overwriteFile) throws IOException {
        createDirectoryIfNotExists(destinationPath);

        if (overwriteFile) {
            Files.copy(source.toPath(), destinationPath, REPLACE_EXISTING);
        } else {
            Files.copy(source.toPath(), destinationPath);
        }

        return destinationPath.toFile();
    }
}
