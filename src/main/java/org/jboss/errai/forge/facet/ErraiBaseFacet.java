package org.jboss.errai.forge.facet;

import org.jboss.errai.forge.ErraiPlugin;
import org.jboss.forge.maven.MavenPluginFacet;
import org.jboss.forge.maven.plugins.ExecutionBuilder;
import org.jboss.forge.maven.plugins.MavenPluginBuilder;
import org.jboss.forge.parser.xml.Node;
import org.jboss.forge.parser.xml.XMLParser;
import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.RequiresFacet;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author pslegr
 */
@Alias("org.jboss.errai")
@RequiresFacet({DependencyFacet.class, WebResourceFacet.class})
public abstract class ErraiBaseFacet extends BaseFacet {
    public static final String SUCCESS_MSG_FMT = "***SUCCESS*** %s %s has been installed.";

    abstract void installErraiDeps();

    boolean isFacetInstalled() {
        return project.hasFacet(getClass());
    }

    public boolean install() {
        installBaseErraiDependencies();
        installGWTPlugin();
        installErraiDeps();
        installBaseGwtModules();
        appendGwtModule();
        return true;
    }

    protected abstract void appendGwtModule();

    protected void installBaseGwtModules() {
        FileResource<?> moduleConfig = getModuleConfig();
        if (!moduleConfig.exists()) {
            InputStream cfStream = ErraiPlugin.class.getResourceAsStream("/App.gwt.xml.txt");
            moduleConfig.setContents(cfStream);
        }
    }

    void appendGwtModule(String moduleName) {
        FileResource<?> moduleConfig = getModuleConfig();
        Node xml = XMLParser.parse(moduleConfig.getResourceInputStream());
        xml.createChild("inherits").attribute("name", moduleName);
        moduleConfig.setContents(XMLParser.toXMLInputStream(xml));
    }


    @Override
    public boolean isInstalled() {
        return isFacetInstalled();
    }

    /**
     * Install the maven dependencies required for Errai
     */
    private void installBaseErraiDependencies() {
        String erraiVersion = Versions.getInstance().getErrai_version();
        String gwtVersion = Versions.getInstance().getGwt_version();
        List<? extends Dependency> dependencies = Arrays.asList(
                DependencyBuilder.create("org.jboss.errai:errai-bus:" + erraiVersion),
                DependencyBuilder.create("org.jboss.errai:errai-ioc:" + erraiVersion),
                DependencyBuilder.create("org.jboss.errai:errai-tools:" + erraiVersion),
                DependencyBuilder.create("com.google.gwt:gwt-servlet:" + gwtVersion),
                DependencyBuilder.create("com.google.gwt:gwt-user:" + gwtVersion)
        );


        DependencyFacet deps = project.getFacet(DependencyFacet.class);
        for (Dependency dependency : dependencies) {
            deps.addDirectDependency(dependency);
        }
    }

    public void installGWTPlugin() {
        String gwtVersion = "";
        for (Dependency dep : project.getFacet(DependencyFacet.class).getDependencies()) {
            if (dep.getGroupId().startsWith("com.google.gwt")) {
                gwtVersion = dep.getVersion();
            }
        }

        DependencyBuilder gwtDependencyBuilder = DependencyBuilder.create()
                .setGroupId("org.codehaus.mojo")
                .setArtifactId("gwt-maven-plugin")
                .setVersion(gwtVersion);

        ExecutionBuilder execution = ExecutionBuilder.create()
                .addGoal("resources")
                .addGoal("compile");
        MavenPluginBuilder gwtPlugin = MavenPluginBuilder.create()
                .setDependency(gwtDependencyBuilder)
                .createConfiguration()
                .createConfigurationElement("logLevel")
                .setText("INFO")
                .getParentPluginConfig().getOrigin()
                .createConfiguration()
                .createConfigurationElement("runTarget")
                .setText("App.html")
                .getParentPluginConfig().getOrigin()
                .createConfiguration()
                .createConfigurationElement("extraJvmArgs")
                .setText("-Xmx512m")
                .getParentPluginConfig().getOrigin()
                .createConfiguration()
                .createConfigurationElement("soyc")
                .setText("false")
                .getParentPluginConfig().getOrigin()
                .createConfiguration()
                .createConfigurationElement("hostedWebapp")
                .setText("src/main/webapp/")
                .getParentPluginConfig().getOrigin()
                .createConfiguration()
                .createConfigurationElement("treeLogger")
                .setText("true")
                .getParentPluginConfig().getOrigin()
                .addExecution(execution);

        MavenPluginFacet pluginFacet = project.getFacet(MavenPluginFacet.class);
        pluginFacet.addPlugin(gwtPlugin);

    }

    public FileResource<?> getModuleConfig() {
        JavaSourceFacet source = project.getFacet(JavaSourceFacet.class);
        DirectoryResource sourceRoot = source.getBasePackageResource();

        return (FileResource<?>) sourceRoot.getChild("App.gwt.xml");
    }
}
