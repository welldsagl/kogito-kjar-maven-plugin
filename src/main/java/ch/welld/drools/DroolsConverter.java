package ch.welld.drools;

import static ch.welld.drools.FileUtils.copyFile;
import static ch.welld.drools.FileUtils.createDirectoryIfNotExists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.drools.workbench.models.guided.dtable.backend.GuidedDTDRLPersistence;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTXMLPersistence;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;

public class DroolsConverter {

    /**
     * Utility function to convert a .gdst file to .drl string.
     *
     * @param gdstFile the file to convert
     * @return the drl equivalent string
     */
    private static String convertGdstFileToDrlString(File gdstFile) throws IOException {
        String decisionTableXml = new String(Files.readAllBytes(gdstFile.toPath()));
        GuidedDecisionTable52 model = GuidedDTXMLPersistence.getInstance().unmarshal(decisionTableXml);
        return GuidedDTDRLPersistence.getInstance().marshal(model);
    }

    /**
     * Converts a .gdst file to .drl and copies it to destination path.
     *
     * @param sourceFile the source file in .gdst format to convert
     * @param destinationPath the destination path to which the file is written
     * @param overwriteFile if true, overwrites the existing file
     * @return the created file
     */
    private static File convertGdst(File sourceFile, Path destinationPath, boolean overwriteFile) throws IOException {
        String drlString = convertGdstFileToDrlString(sourceFile);
        createDirectoryIfNotExists(destinationPath);
        File targetFile = new File(
            destinationPath.toFile(),
            sourceFile.getName().replace(".gdst", ".drl")
        );
        if (overwriteFile || !targetFile.exists()) {
            Files.write(targetFile.toPath(), drlString.getBytes());
        }
        return targetFile;
    }

    /**
     * Copies knowledge source file to a given directory.
     *
     * @param sourceFile the source file to copy. If it is a .gdst file, it is also converted to .drl
     * @param destinationPath the destination path to which the file is written
     * @param overwriteFile if true, overwrites the existing file
     * @return the created file
     */
    public static File copyKnowledge(File sourceFile, Path destinationPath, boolean overwriteFile) throws IOException {
        // FIXME: if the knowledge contains both `rules.drl` and `rules.dgst` the behaviour could be non-deterministic
        //  (depending on which one gets copied first) and the value of @param{overwriteFile}
        String ext = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
        switch (ext) {
            // TODO: move to factory when we need to add more cases
            case ".gdst":
                return convertGdst(sourceFile, destinationPath, overwriteFile);
            case ".drl":
                return copyFile(sourceFile, destinationPath.resolve(sourceFile.getName()), overwriteFile);
            default:
                throw new IOException("Cannot convert file with extension " + ext);
        }
    }
}
