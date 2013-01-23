package org.jboss.errai.forge;

import java.io.InputStream;

import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.project.facets.ResourceFacet;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.plugins.PipeOut;

public class ErraiCdiExample extends ErraiExample{
	
	public ErraiCdiExample(ErraiPlugin plugin, final PipeOut pipeOut) {
		super(plugin, pipeOut);
   }
	
    /* (non-Javadoc)
     * @see org.jboss.errai.forge.ErraiExample#createWebappFiles(org.jboss.forge.shell.plugins.PipeOut)
     */
    void createWebappFiles(final PipeOut pipeOut) {
        DirectoryResource webRoot = plugin.getProject().getFacet(WebResourceFacet.class).getWebRootDirectory();
        // create WEB-INF/web.xml
        DirectoryResource wiDirectory = webRoot.getOrCreateChildDirectory("WEB-INF");
        FileResource<?> wiPage = (FileResource<?>) wiDirectory.getChild("web.xml");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/webapp/WEB-INF/web.xml");
        wiPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "WEB-INF/web.xml", "file"));
        
        FileResource<?> beansPage = (FileResource<?>) wiDirectory.getChild("beans.xml");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/webapp/WEB-INF/beans.xml");
        beansPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "WEB-INF/beans.xml", "file"));

        FileResource<?> jePage = (FileResource<?>) wiDirectory.getChild("jetty-env.xml");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/webapp/WEB-INF/jetty-env.xml");
        jePage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "WEB-INF/jetty-env.xml", "file"));


        // create App.css
        FileResource<?> appPage = (FileResource<?>) webRoot.getChild("App.css");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/webapp/App.css");
        appPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "App.css", "file"));

        // create App.html
        FileResource<?> apphPage = (FileResource<?>) webRoot.getChild("App.html");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/webapp/App.html");
        apphPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "App.html", "file"));
        
        // create index.html
        FileResource<?> indexPage = (FileResource<?>) webRoot.getChild("index.html");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/webapp/index.html");
        indexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "index.html", "file"));
        
    }

    /* (non-Javadoc)
     * @see org.jboss.errai.forge.ErraiExample#createAppFiles(org.jboss.forge.shell.plugins.PipeOut)
     */
    void createAppFiles(final PipeOut pipeOut) {
        JavaSourceFacet source = plugin.getProject().getFacet(JavaSourceFacet.class);
        DirectoryResource sourceRoot = source.getBasePackageResource();
        
        DirectoryResource clientDirectory = sourceRoot.getOrCreateChildDirectory("client");
        DirectoryResource localDirectory = clientDirectory.getOrCreateChildDirectory("local");
        DirectoryResource sharedDirectory = clientDirectory.getOrCreateChildDirectory("shared");
        DirectoryResource srvDirectory = sourceRoot.getOrCreateChildDirectory("server");
        
        //create client classes
        FileResource<?> localIndexPage = (FileResource<?>) localDirectory.getChild("App.java");
        InputStream cStream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/java/client/local/App.java.txt");
        localIndexPage.setContents(Utils.replacePackageName(cStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "App", "class"));
        
        FileResource<?> shared1IndexPage = (FileResource<?>) sharedDirectory.getChild("HelloMessage.java");
        cStream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/java/client/shared/HelloMessage.java.txt");
        shared1IndexPage.setContents(Utils.replacePackageName(cStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "HelloMessage", "class"));
        
        FileResource<?> shared2IndexPage = (FileResource<?>) sharedDirectory.getChild("Response.java");
        cStream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/java/client/shared/Response.java.txt");
        shared2IndexPage.setContents(Utils.replacePackageName(cStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "Response", "class"));
        
        //create server class
        FileResource<?> serverIndexPage = (FileResource<?>) srvDirectory.getChild("SimpleCDIService.java");
        InputStream sStream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/java/server/SimpleCDIService.java.txt");
        serverIndexPage.setContents(Utils.replacePackageName(sStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "SimpleCDIService", "class"));
    }
    
    /* (non-Javadoc)
     * @see org.jboss.errai.forge.ErraiExample#createResourceFiles(org.jboss.forge.shell.plugins.PipeOut)
     */
    void createResourceFiles(final PipeOut pipeOut) {
        DirectoryResource sourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getResourceFolder();
        
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiApp.properties");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/resources/ErraiApp.properties");
        appIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiApp.properties", "file"));
        
        //create Service props
        FileResource<?> serviceIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiService.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/resources/ErraiService.properties");
        serviceIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiService.properties", "file"));
        
        //create log4j props
        FileResource<?> log4jIndexPage = (FileResource<?>) sourceRoot.getChild("log4j.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/resources/log4j.properties");
        log4jIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "log4j.properties", "file"));
        
        //create login config
        FileResource<?> loginIndexPage = (FileResource<?>) sourceRoot.getChild("login.config");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/resources/login.config");
        loginIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "login.config", "file"));
        
        //create roles properties
        FileResource<?> rolesIndexPage = (FileResource<?>) sourceRoot.getChild("roles.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/resources/roles.properties");
        rolesIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "roles.properties", "file"));
        
        //create users properties
        FileResource<?> usersIndexPage = (FileResource<?>) sourceRoot.getChild("users.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/resources/users.properties");
        usersIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "users.properties", "file"));
    }    
    
    /* (non-Javadoc)
     * @see org.jboss.errai.forge.ErraiExample#createTestFiles(org.jboss.forge.shell.plugins.PipeOut)
     */
    void createTestFiles(final PipeOut pipeOut) {
        DirectoryResource resourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getTestResourceFolder();
        
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiApp.properties");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/test/resources/ErraiApp.properties");
        appIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiApp.properties", "file"));
        
        FileResource<?> appsIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiService.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/test/resources/ErraiService.properties");
        appsIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiService.properties", "file"));
        
        FileResource<?> jndiIndexPage = (FileResource<?>) resourceRoot.getChild("jndi.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/test/resources/jndi.properties");
        jndiIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "jndi.properties", "file"));

        //create Test classes
        DirectoryResource javaRoot = plugin.getProject().getFacet(JavaSourceFacet.class).getTestSourceFolder();
        DirectoryResource clDirectory = javaRoot.getOrCreateChildDirectory("client");
        clDirectory = clDirectory.getOrCreateChildDirectory("local");
        
        //create client class
        FileResource<?> cl1IndexPage = (FileResource<?>) clDirectory.getChild("AppIntegrationTest.java");
        InputStream c1Stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/test/java/client/local/AppIntegrationTest.java.txt");
        cl1IndexPage.setContents(Utils.replacePackageName(c1Stream, plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "AppIntegrationTest.java", "class"));
        
        //create client class
        FileResource<?> cl2IndexPage = (FileResource<?>) clDirectory.getChild("CDITestHelper.java");
        InputStream c2Stream = ErraiPlugin.class.getResourceAsStream("/errai-cdi/test/java/client/local/CDITestHelper.java.txt");
        cl2IndexPage.setContents(Utils.replacePackageName(c2Stream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "CDITestHelper.java", "class"));
    }
    
    //TODO implement uninstall methods


	@Override
	void deleteWebappFiles(PipeOut pipeOut) {
        DirectoryResource webRoot = plugin.getProject().getFacet(WebResourceFacet.class).getWebRootDirectory();
        // create WEB-INF/web.xml
        DirectoryResource wiDirectory = webRoot.getOrCreateChildDirectory("WEB-INF");
        wiDirectory.delete(true);

        FileResource<?> appPage = (FileResource<?>) webRoot.getChild("App.css");
        appPage.delete(true);

        FileResource<?> apphPage = (FileResource<?>) webRoot.getChild("App.html");
        apphPage.delete(true);
        
        FileResource<?> indexPage = (FileResource<?>) webRoot.getChild("index.html");
        indexPage.delete(true);
	}

	@Override
	void deleteAppFiles(PipeOut pipeOut) {
        JavaSourceFacet source = plugin.getProject().getFacet(JavaSourceFacet.class);
        DirectoryResource sourceRoot = source.getBasePackageResource();
        
        DirectoryResource clientDirectory = sourceRoot.getOrCreateChildDirectory("client");
        clientDirectory.delete(true);
        DirectoryResource srvDirectory = sourceRoot.getOrCreateChildDirectory("server");
        srvDirectory.delete(true);
        
        FileResource<?> confIndexPage = (FileResource<?>) sourceRoot.getChild("App.gwt.xml");
        confIndexPage.delete(true);
	}

	@Override
	void deleteResourceFiles(PipeOut pipeOut) {
        DirectoryResource sourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getResourceFolder();
        
        FileResource<?> appIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiApp.properties");
        appIndexPage.delete(true);
        
        FileResource<?> serviceIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiService.properties");
        serviceIndexPage.delete(true);
        
        FileResource<?> log4jIndexPage = (FileResource<?>) sourceRoot.getChild("log4j.properties");
        log4jIndexPage.delete(true);
        
        FileResource<?> loginIndexPage = (FileResource<?>) sourceRoot.getChild("login.config");
        loginIndexPage.delete(true);
        
        FileResource<?> rolesIndexPage = (FileResource<?>) sourceRoot.getChild("roles.properties");
        rolesIndexPage.delete(true);
        
        FileResource<?> usersIndexPage = (FileResource<?>) sourceRoot.getChild("users.properties");
        usersIndexPage.delete(true);
	}

	@Override
	void deleteTestFiles(PipeOut pipeOut) {
        DirectoryResource resourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getTestResourceFolder();
        
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiApp.properties");
        appIndexPage.delete(true);
        
        FileResource<?> appsIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiService.properties");
        appsIndexPage.delete(true);
        
        FileResource<?> jndiIndexPage = (FileResource<?>) resourceRoot.getChild("jndi.properties");
        jndiIndexPage.delete(true);

        //create Test classes
        DirectoryResource javaRoot = plugin.getProject().getFacet(JavaSourceFacet.class).getTestSourceFolder();
        DirectoryResource clDirectory = javaRoot.getOrCreateChildDirectory("client");
        clDirectory.delete(true);
        
	}
    
}
