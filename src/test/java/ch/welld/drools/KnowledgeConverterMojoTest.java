package ch.welld.drools;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;
import java.io.File;

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
}

