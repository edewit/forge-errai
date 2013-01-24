package org.jboss.errai.forge.facet;

import org.jboss.errai.forge.ErraiPlugin;
import org.jboss.errai.forge.Utils;
import org.jboss.forge.env.Configuration;
import org.jboss.forge.maven.MavenCoreFacet;
import org.jboss.forge.parser.xml.Node;
import org.jboss.forge.parser.xml.XMLParser;
import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.DependencyInstaller;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.plugins.RequiresFacet;

import javax.inject.Inject;
import java.io.InputStream;

/**
 * @author edewit@redhat.com
 */
@RequiresFacet(ErraiBaseFacet.class)
public class ErraiCordovaFacet extends ErraiFacet {
    public static final String URL = "url";
    private static final String TEMPLATE_NAME = "Config.java";
    private static final String ERRAI_CORDOVA_TEMPLATE_LOCATION = "/errai-cordova/java/";

    private static final Dependency CORDOVA_DEPENDENCY = DependencyBuilder.create()
            .setGroupId("org.jboss.errai")
            .setArtifactId("errai-cordova")
            .setVersion(Versions.getInstance().getErrai_version());

    @Inject
    public DependencyInstaller installer;

    @Inject
    public Configuration configuration;

    @Override
    public boolean install() {
        installer.install(project, CORDOVA_DEPENDENCY);
        appendGwtModule("org.jboss.errai.ui.Cordova");
        addModuduleConfiguration();
        addRemoteServerLocation();
        return true;
    }

    private void addModuduleConfiguration() {
        FileResource<?> moduleConfig = getModuleConfig();
        Node xml = XMLParser.parse(moduleConfig.getResourceInputStream());

        Node config = xml.createChild("replace-with");
        config.attribute("class", getJavaSourceFacet().getBasePackage() + ".local.Config");
        config.createChild("when-type-is").attribute("class", "org.jboss.errai.bus.client.framework.Configuration");

        moduleConfig.setContents(XMLParser.toXMLInputStream(xml));
    }

    private void addRemoteServerLocation() {
        JavaSourceFacet source = getJavaSourceFacet();
        DirectoryResource sourceRoot = source.getBasePackageResource();

        DirectoryResource clientDirectory = sourceRoot.getOrCreateChildDirectory("client");
        DirectoryResource localDirectory = clientDirectory.getOrCreateChildDirectory("local");

        FileResource<?> configJava = (FileResource<?>) localDirectory.getChild(TEMPLATE_NAME);
        String templateName = ERRAI_CORDOVA_TEMPLATE_LOCATION + TEMPLATE_NAME + ".txt";
        InputStream inputStream = ErraiPlugin.class.getResourceAsStream(templateName);
        String contents = Utils.replacePackageName(inputStream, project);
        configJava.setContents(contents.replace("${remoteLocation}", configuration.getString(URL) + getArtifactId()));
    }

    private JavaSourceFacet getJavaSourceFacet() {
        return project.getFacet(JavaSourceFacet.class);
    }

    private String getArtifactId() {
        return project.getFacet(MavenCoreFacet.class).getPOM().getArtifactId();
    }

    @Override
    public boolean isInstalled() {
        return installer.isInstalled(project, CORDOVA_DEPENDENCY);
    }
}
