package org.jboss.errai.forge;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiBusFacet;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.ResourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.resources.Resource;
import org.jboss.forge.test.AbstractShellTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class PluginTest extends AbstractShellTest {
    @Deployment
    public static JavaArchive getDeployment() {
        return AbstractShellTest.getDeployment().addPackages(true, ErraiPlugin.class.getPackage());
    }

    @Test
    public void shouldInstall() throws Exception {
        //given
        Project project = setupErraiProject();

        //when
        getShell().execute("errai install-errai-bus");

        //then
        DirectoryResource sourceRoot = project.getFacet(ResourceFacet.class).getResourceFolder();
        Resource<?> erraiProperties = sourceRoot.getChild("ErraiApp.properties");

        assertEquals("ErraiApp.properties", erraiProperties.getName());
        assertTrue(project.hasFacet(ErraiBusFacet.class));
        ErraiBusFacet facet = project.getFacet(ErraiBusFacet.class);
        assertTrue(facet.isInstalled());
    }

    @Test
    public void shouldNotInstallTwice() throws Exception {
        //given
        setupErraiProject();

        //when
        getShell().execute("errai install-errai-bus");
        getShell().execute("errai install-errai-bus");

        //then
        String[] lines = getOutput().split("\n");
        String lastLine = lines[lines.length - 1];
        assertEquals("Errai Bus is installed.", lastLine);
    }

    private Project setupErraiProject() throws Exception {
        return setupErraiProject("\n");
    }

    private Project setupErraiProject(String type) throws Exception {
        Project project = initializeJavaProject();
        queueInputLines(type, "\n"); //errai-bus, change project to war
        getShell().execute("errai setup");
        return project;
    }

    @Test
    public void shouldCreateInitialGwtModuleConfig() throws Exception {
        //given
        Project project = setupErraiProject();

        //then
        FileResource<?> config = getGwtModuleConfig(project);
        assertTrue(config.exists());
    }

    @Test
    public void shouldAppendGwtModuleConfig() throws Exception {
        //given
        Project project = setupErraiProject("4"); //errai-ui

        //then
        Scanner scanner = null;
        try {
            FileResource<?> config = getGwtModuleConfig(project);
            scanner = new Scanner(config.getResourceInputStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if ("<inherits name=\"org.jboss.errai.ui.UI\"/>".equals(line.trim())) {
                    return;
                }
            }
            fail("didn't find errai ui module definition");
        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    private FileResource<?> getGwtModuleConfig(Project project) {
        return project.getFacet(ErraiBaseFacet.class).getModuleConfig();
    }

}