package ch.welld.drools;

import org.drools.workbench.models.guided.dtable.backend.GuidedDTDRLPersistence;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTXMLPersistence;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ch.welld.drools.FileUtils.createDirectoryIfNotExists;
import static ch.welld.drools.FileUtils.getRelativePath;

public class DroolsConverter {

    private static String convertGdstFileToDrlString(File gdstFile) throws IOException {
        String decisionTableXml = new String (
                Files.readAllBytes(Paths.get(gdstFile.getPath()) ) );
        GuidedDecisionTable52 model = GuidedDTXMLPersistence.getInstance().unmarshal( decisionTableXml );

        return GuidedDTDRLPersistence.getInstance().marshal( model );
    }

    public static File convertGdst(File sourceFile, File outputDirectory, String basePath, boolean deleteInputFiles) throws IOException {
        String drlString = convertGdstFileToDrlString(sourceFile);
        String relativePath = getRelativePath(sourceFile.getAbsolutePath(), basePath);

        File fullOutputDirectory = new File(outputDirectory, relativePath);
        createDirectoryIfNotExists(fullOutputDirectory);
        File targetFile = new File( fullOutputDirectory, sourceFile.getName().replace(".gdst", ".drl") );
        Files.write(Paths.get(targetFile.getPath()), drlString.getBytes());

        if(deleteInputFiles) {
            sourceFile.delete();
        }
        return targetFile;
    }

}
