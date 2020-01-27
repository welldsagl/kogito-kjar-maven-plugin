package ch.welld.kie;

import static ch.welld.kie.FileUtils.copyFile;
import static ch.welld.kie.FileUtils.createDirectoryIfNotExists;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.drools.workbench.models.guided.dtable.backend.GuidedDTDRLPersistence;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTXMLPersistence;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;
import org.drools.workbench.models.guided.template.backend.RuleTemplateModelDRLPersistenceImpl;
import org.drools.workbench.models.guided.template.backend.RuleTemplateModelXMLPersistenceImpl;
import org.drools.workbench.models.guided.template.shared.TemplateModel;

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
     * Utility function to convert a .template file to .drl string.
     *
     * @param templateFile the file to convert
     * @return the drl equivalent string
     */
    private static String convertGuidedTemplateFileToDrlString(File templateFile) throws IOException {
        String guidedTemplateXml = new String(Files.readAllBytes(templateFile.toPath()));
        TemplateModel model = RuleTemplateModelXMLPersistenceImpl.getInstance().unmarshal( guidedTemplateXml );
        return RuleTemplateModelDRLPersistenceImpl.getInstance().marshal(model);
    }

    /**
     * Utility function to generate a drl file
     * @param drlString the content of the file
     * @param fileName the name of the file
     * @param destinationPath the destination path to which the file is written
     * @param overwriteFile if true, overwrites the existing file
     * @return the created file
     */
    private static File createDrlFile(String drlString, String fileName, Path destinationPath, boolean overwriteFile) throws IOException {
        createDirectoryIfNotExists(destinationPath);
        File targetFile = new File(destinationPath.toFile(), fileName);
        if (overwriteFile || !targetFile.exists()) {
            Files.write(targetFile.toPath(), drlString.getBytes());
        }
        return targetFile;
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
        String fileName = sourceFile.getName().replace(".gdst", ".drl");
        return createDrlFile(drlString, fileName, destinationPath, overwriteFile);
    }

    /**
     * Converts a .template file to .drl and copies it to destination path.
     *
     * @param sourceFile the source file in .template format to convert
     * @param destinationPath the destination path to which the file is written
     * @param overwriteFile if true, overwrites the existing file
     * @return the created file
     */
    private static File convertGuidedTemplate(File sourceFile, Path destinationPath, boolean overwriteFile) throws IOException {
        String drlString = convertGuidedTemplateFileToDrlString(sourceFile);
        String fileName = sourceFile.getName().replace(".template", ".drl");
        return createDrlFile(drlString, fileName, destinationPath, overwriteFile);
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
            case ".template":
                return convertGuidedTemplate(sourceFile, destinationPath, overwriteFile);
            case ".drl":
                return copyFile(sourceFile, destinationPath.resolve(sourceFile.getName()), overwriteFile);
            default:
                throw new IOException("Cannot convert file with extension " + ext);
        }
    }
}
