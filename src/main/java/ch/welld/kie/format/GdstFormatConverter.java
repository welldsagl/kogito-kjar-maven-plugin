package ch.welld.kie.format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.drools.workbench.models.guided.dtable.backend.GuidedDTDRLPersistence;
import org.drools.workbench.models.guided.dtable.backend.GuidedDTXMLPersistence;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;

/**
 * A format converter for Guided Decision Table (.gdst) files.
 */
public class GdstFormatConverter implements KnowledgeFormatConverter {

    /**
     * Converts a .gdst file to .drl
     *
     * @param sourceFile the source file in .gdst format to convert
     * @return the equivalent rules in DRL format
     */
    @Override
    public String convertToDrl(File sourceFile) throws IOException {
        String decisionTableXml = new String(Files.readAllBytes(sourceFile.toPath()));
        GuidedDecisionTable52 model = GuidedDTXMLPersistence.getInstance().unmarshal(decisionTableXml);
        return GuidedDTDRLPersistence.getInstance().marshal(model);
    }

    @Override
    public String supportedFormat() {
        return ".gdst";
    }
}
