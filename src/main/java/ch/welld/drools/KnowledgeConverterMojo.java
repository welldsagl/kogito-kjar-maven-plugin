package ch.welld.drools;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ch.welld.drools.DroolsConverter.copyKnowledge;
import static ch.welld.drools.FileUtils.createDirectoryIfNotExists;
import static ch.welld.drools.FileUtils.getAllFilesWithExtension;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "convert-knowledge", defaultPhase = LifecyclePhase.PACKAGE )
public class KnowledgeConverterMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDirectory", required = true )
    private File outputDirectory;

    @Parameter( defaultValue = "${project.build.directory}", property = "inputDirectory", required = true )
    private File inputDirectory;

    @Parameter( defaultValue = "false", property = "overrideFiles" )
    private boolean overrideFiles;

    private void logParameters() {
        getLog().info("--- PARAMETERS ---");
        getLog().info("Output directory: " + outputDirectory);
        getLog().info("Input directory: " + inputDirectory);
        getLog().info("--- ---------- ---");
    }

    public void execute() {
        logParameters();

        /* Create the output directory if it does not exist */
        createDirectoryIfNotExists(outputDirectory);

        /* Get all knowledge files in input directory */
        List<File> knowledgeFiles = getAllFilesWithExtension(inputDirectory, Arrays.asList(".gdst", ".drl"));

        getLog().info("Knowledge files found: " + knowledgeFiles.size());
        knowledgeFiles.forEach(knowledgeFile -> getLog().info(" - " + knowledgeFile.getPath()));

        /* Convert all found knowledge files into drl files */
        knowledgeFiles.forEach(sourceFile -> {
            try {
                getLog().info("Copying file " + sourceFile.getPath());
                copyKnowledge(sourceFile, outputDirectory, inputDirectory.getPath(), overrideFiles);
            } catch(IOException ex) {
                getLog().error("Cannot convert file " + sourceFile.getName());
            }
        });

    }
}
