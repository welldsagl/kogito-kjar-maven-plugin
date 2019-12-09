package ch.welld.drools;


import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GdstConverterMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    /**
     * Test that the Mojo can be run
     */
    @Test
    public void testSetup()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        GdstConverterMojo gdstConverterMojo = ( GdstConverterMojo ) rule.lookupConfiguredMojo( pom, "convert-gdst" );
        assertNotNull( gdstConverterMojo );
    }

    /**
     * Integration test of entire plugin
     */
    @Test
    public void testCompletePlugin()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        GdstConverterMojo gdstConverterMojo = ( GdstConverterMojo ) rule.lookupConfiguredMojo( pom, "convert-gdst" );
        assertNotNull( gdstConverterMojo );
        gdstConverterMojo.execute();

        File inputDirectory = ( File ) rule.getVariableValueFromObject( gdstConverterMojo, "inputDirectory" );
        assertNotNull( inputDirectory );
        assertTrue( inputDirectory.exists() );

        File outputDirectory = ( File ) rule.getVariableValueFromObject( gdstConverterMojo, "outputDirectory" );
        assertNotNull( outputDirectory );
        assertTrue( outputDirectory.exists() );

        File drlFile = new File( outputDirectory, "GdsSample.drl" );
        assertTrue( drlFile.exists() );
    }
}

