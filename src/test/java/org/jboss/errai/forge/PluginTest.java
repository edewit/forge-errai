package org.jboss.errai.forge;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.errai.forge.facet.ErraiBusFacet;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.ResourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.Resource;
import org.jboss.forge.test.AbstractShellTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        Project project = initializeJavaProject();
        queueInputLines("\n", "\n"); //errai-bus, change project to war
        getShell().execute("errai setup");
        return project;
    }

}