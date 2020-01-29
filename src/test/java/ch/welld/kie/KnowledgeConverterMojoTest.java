package ch.welld.kie;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;
import java.io.File;

import static org.apache.maven.plugin.testing.ArtifactStubFactory.setVariableValueToObject;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class KnowledgeConverterMojoTest {

    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
            File outputDirectory = new File("target/test-classes/project-to-test/output");
            FileUtils.deleteDirectory(outputDirectory);
        }
    };

    /**
     * Test that the Mojo can be run
     */
    @Test
    public void testSetup()  throws Exception {
        File pom = new File("target/test-classes/project-to-test/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        KnowledgeConverterMojo knowledgeConverterMojo =
                (KnowledgeConverterMojo) rule.lookupConfiguredMojo(pom, "convert-knowledge");
        assertNotNull(knowledgeConverterMojo);
    }

    /**
     * Integration test of entire plugin
     */
    @Test
    public void testCompletePlugin() throws Exception {
        File pom = new File( "target/test-classes/project-to-test/" );

        KnowledgeConverterMojo knowledgeConverterMojo =
                (KnowledgeConverterMojo) rule.lookupConfiguredMojo(pom, "convert-knowledge");
        setVariableValueToObject(knowledgeConverterMojo, "overwriteFiles", true);
        assertNotNull(knowledgeConverterMojo);
        knowledgeConverterMojo.execute();

        File inputDirectory = (File) rule.getVariableValueFromObject(knowledgeConverterMojo, "inputDirectory");
        assertNotNull(inputDirectory);
        assertTrue(inputDirectory.exists());

        File outputDirectory = (File) rule.getVariableValueFromObject(knowledgeConverterMojo, "outputDirectory");
        assertNotNull(outputDirectory);
        assertTrue(outputDirectory.exists());

        File drlFile = new File( outputDirectory, "GdsSample.drl" );
        assertTrue(drlFile.exists());
    }

    @Test
    public void testCompletePluginWithoutOverwriteFiles() throws Exception {
        File pom = new File( "target/test-classes/project-to-test/" );

        KnowledgeConverterMojo knowledgeConverterMojo =
                (KnowledgeConverterMojo) rule.lookupConfiguredMojo(pom, "convert-knowledge");
        setVariableValueToObject(knowledgeConverterMojo, "overwriteFiles", false);
        assertNotNull(knowledgeConverterMojo);
        knowledgeConverterMojo.execute();

        File inputDirectory = (File) rule.getVariableValueFromObject(knowledgeConverterMojo, "inputDirectory");
        assertNotNull(inputDirectory);
        assertTrue(inputDirectory.exists());

        File outputDirectory = (File) rule.getVariableValueFromObject(knowledgeConverterMojo, "outputDirectory");
        assertNotNull(outputDirectory);
        assertTrue(outputDirectory.exists());

        File drlFileFromGdst = new File( outputDirectory, "GdsSample.drl" );
        assertTrue(drlFileFromGdst.exists());

        File drlFileFromGuidedTemplate = new File( outputDirectory, "GuidedTemplateSample.drl" );
        assertTrue(drlFileFromGuidedTemplate.exists());
    }

//    /**
//     * Integration test of entire plugin
//     */
//    @Test
//    public void testComplete2Plugin() throws Exception {
//        File pom = new File( "target/test-classes/project-to-test2/" );
//
//        KnowledgeConverterMojo knowledgeConverterMojo =
//                (KnowledgeConverterMojo) rule.lookupConfiguredMojo(pom, "convert-knowledge");
//        assertNotNull(knowledgeConverterMojo);
//        knowledgeConverterMojo.execute();
//
//        File inputDirectory = (File) rule.getVariableValueFromObject(knowledgeConverterMojo, "inputDirectory");
//        assertNotNull(inputDirectory);
//        assertTrue(inputDirectory.exists());
//
//        File outputDirectory = (File) rule.getVariableValueFromObject(knowledgeConverterMojo, "outputDirectory");
//        assertNotNull(outputDirectory);
//        assertTrue(outputDirectory.exists());
//
//        File drlFile = new File( outputDirectory, "GdsSample.drl" );
//        assertTrue(drlFile.exists());
//    }
}

