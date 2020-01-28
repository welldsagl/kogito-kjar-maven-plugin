package ch.welld.kie.format;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.drools.workbench.models.guided.template.backend.RuleTemplateModelDRLPersistenceImpl;
import org.drools.workbench.models.guided.template.backend.RuleTemplateModelXMLPersistenceImpl;
import org.drools.workbench.models.guided.template.shared.TemplateModel;

public class TemplateFormatConverter implements KnowledgeFormatConverter {

    /**
     * Converts a .template file to .drl and copies it to destination path.
     *
     * @param sourceFile the source file in .template format to convert
     * @return the equivalent rules in DRL format
     */
    @Override
    public String convertToDrl(File sourceFile) throws IOException {
        String guidedTemplateXml = new String(Files.readAllBytes(sourceFile.toPath()));
        TemplateModel model = RuleTemplateModelXMLPersistenceImpl.getInstance().unmarshal(guidedTemplateXml);
        return RuleTemplateModelDRLPersistenceImpl.getInstance().marshal(model);
    }

    @Override
    public String supportedFormat() {
        return ".template";
    }
}
