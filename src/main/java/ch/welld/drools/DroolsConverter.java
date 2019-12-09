package ch.welld.drools;

import org.drools.workbench.models.guided.dtable.backend.GuidedDTDRLPersistence;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTXMLPersistence;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ch.welld.drools.FileUtils.*;

public class DroolsConverter {

    private static String convertGdstFileToDrlString(File gdstFile) throws IOException {
        String decisionTableXml = new String (
                Files.readAllBytes(Paths.get(gdstFile.getPath()) ) );
        GuidedDecisionTable52 model = GuidedDTXMLPersistence.getInstance().unmarshal( decisionTableXml );

        return GuidedDTDRLPersistence.getInstance().marshal( model );
    }

    public static File copyKnowledge(File sourceFile, File outputDirectory, String basePath, boolean overrideFiles) throws IOException {
        String ext = sourceFile.getName().substring(sourceFile.getName().lastIndexOf("."));
        switch (ext) {
            case ".gdst":
                return convertGdst(sourceFile, outputDirectory, basePath, overrideFiles);
            case ".drl":
                return copyFile(sourceFile, new File(outputDirectory, getRelativePath(sourceFile.getAbsolutePath(), basePath)), overrideFiles);
            default:
                throw new IOException("Cannot convert file with extension " + ext);
        }
    }

    private static File convertGdst(File sourceFile, File outputDirectory, String basePath, boolean overrideFiles) throws IOException {
        String drlString = convertGdstFileToDrlString(sourceFile);
        String relativePath = getRelativePath(sourceFile.getAbsolutePath(), basePath);

        File fullOutputDirectory = new File(outputDirectory, relativePath);
        createDirectoryIfNotExists(fullOutputDirectory);
        File targetFile = new File( fullOutputDirectory, sourceFile.getName().replace(".gdst", ".drl") );
        if(overrideFiles || !targetFile.exists()) {
            Files.write(Paths.get(targetFile.getPath()), drlString.getBytes());
        }

        return targetFile;
    }

}
