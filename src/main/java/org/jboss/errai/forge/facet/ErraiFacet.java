package org.jboss.errai.forge.facet;

import org.jboss.forge.parser.xml.Node;
import org.jboss.forge.parser.xml.XMLParser;
import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;

/**
 * @author edewit@redhat.com
 */
public abstract class ErraiFacet extends BaseFacet {

    void appendGwtModule(String moduleName) {
        FileResource<?> moduleConfig = getModuleConfig();
        Node xml = XMLParser.parse(moduleConfig.getResourceInputStream());
        xml.createChild("inherits").attribute("name", moduleName);
        moduleConfig.setContents(XMLParser.toXMLInputStream(xml));
    }

    public FileResource<?> getModuleConfig() {
        JavaSourceFacet source = project.getFacet(JavaSourceFacet.class);
        DirectoryResource sourceRoot = source.getBasePackageResource();

        return (FileResource<?>) sourceRoot.getChild("App.gwt.xml");
    }
}
