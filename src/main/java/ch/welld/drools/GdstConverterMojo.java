package ch.welld.drools;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static ch.welld.drools.DroolsConverter.convertGdst;
import static ch.welld.drools.FileUtils.createDirectoryIfNotExists;
import static ch.welld.drools.FileUtils.getAllFilesWithExtension;

/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "convert-gdst", defaultPhase = LifecyclePhase.PACKAGE )
public class GdstConverterMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDirectory", required = true )
    private File outputDirectory;

    @Parameter( defaultValue = "${project.build.directory}", property = "inputDirectory", required = true )
    private File inputDirectory;

    @Parameter( defaultValue = "false", property = "deleteInputFiles" )
    private boolean deleteInputFiles;

    private void logParameters() {
        getLog().info("--- PARAMETERS ---");
        getLog().info("Output directory: " + outputDirectory);
        getLog().info("Input directory: " + inputDirectory);
        getLog().info("--- ---------- ---");
    }

    public void execute()
        throws MojoExecutionException
    {
        logParameters();

        /* Create the output directory if it does not exist */
        createDirectoryIfNotExists(outputDirectory);

        /* Get all gdst files in input directory */
        List<File> gdstFiles = getAllFilesWithExtension(inputDirectory, ".gdst");

        getLog().info("GDST Files found: " + gdstFiles.size());
        gdstFiles.forEach(gdstFile -> getLog().info(" - " + gdstFile.getName()));

        /* Convert all found gdst files into drl files */
        gdstFiles.forEach(sourceFile -> {
            try{
                convertGdst(sourceFile, outputDirectory, inputDirectory.getPath(), deleteInputFiles);
            }catch(IOException ex) {
                getLog().error("Cannot convert file " + sourceFile.getName());
            }
        });
    }
}
