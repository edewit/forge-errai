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

public class ErraiJaxrsExample extends ErraiExample{
	
	public ErraiJaxrsExample(ErraiPlugin plugin, final PipeOut pipeOut) {
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
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/webapp/WEB-INF/web.xml");
        wiPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "WEB-INF/web.xml", "file"));
        
        // create App.css
        FileResource<?> appPage = (FileResource<?>) webRoot.getChild("App.css");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/webapp/App.css");
        appPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "App.css", "file"));

        // create App.html
        FileResource<?> apphPage = (FileResource<?>) webRoot.getChild("App.html");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/webapp/App.html");
        apphPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "App.html", "file"));
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
        InputStream cStream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/java/client/local/App.java.txt");
        localIndexPage.setContents(Utils.replacePackageName(cStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "App", "class"));
        
        FileResource<?> shared1IndexPage = (FileResource<?>) sharedDirectory.getChild("Customer.java");
        cStream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/java/client/shared/Customer.java.txt");
        shared1IndexPage.setContents(Utils.replacePackageName(cStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "Customer", "class"));
        
        FileResource<?> shared2IndexPage = (FileResource<?>) sharedDirectory.getChild("CustomerService.java");
        cStream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/java/client/shared/CustomerService.java.txt");
        shared2IndexPage.setContents(Utils.replacePackageName(cStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "CustomerService", "class"));
        
        //create server class
        FileResource<?> serverIndexPage = (FileResource<?>) srvDirectory.getChild("CustomerServiceImpl.java");
        InputStream sStream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/java/server/CustomerServiceImpl.java.txt");
        serverIndexPage.setContents(Utils.replacePackageName(sStream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "CustomerServiceImpl", "class"));
    }
    
    /* (non-Javadoc)
     * @see org.jboss.errai.forge.ErraiExample#createResourceFiles(org.jboss.forge.shell.plugins.PipeOut)
     */
    void createResourceFiles(final PipeOut pipeOut) {
        DirectoryResource sourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getResourceFolder();
        
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiApp.properties");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/resources/ErraiApp.properties");
        appIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiApp.properties", "file"));
        
        //create Service props
        FileResource<?> serviceIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiService.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/resources/ErraiService.properties");
        serviceIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiService.properties", "file"));
        
        //create log4j props
        FileResource<?> log4jIndexPage = (FileResource<?>) sourceRoot.getChild("log4j.properties");
        stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/resources/log4j.properties");
        log4jIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "log4j.properties", "file"));
    }    
    
    /* (non-Javadoc)
     * @see org.jboss.errai.forge.ErraiExample#createTestFiles(org.jboss.forge.shell.plugins.PipeOut)
     */
    void createTestFiles(final PipeOut pipeOut) {
        DirectoryResource resourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getTestResourceFolder();
        
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiApp.properties");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/test/resources/ErraiApp.properties");
        appIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiApp.properties", "file"));
        
        //create Test classes
        DirectoryResource javaRoot = plugin.getProject().getFacet(JavaSourceFacet.class).getTestSourceFolder();
        DirectoryResource clDirectory = javaRoot.getOrCreateChildDirectory("client");
        clDirectory = clDirectory.getOrCreateChildDirectory("local");
        
        //create client class
        FileResource<?> cl1IndexPage = (FileResource<?>) clDirectory.getChild("ClientUiTest.java");
        InputStream c1Stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/test/java/client/local/ClientUiTest.java.txt");
        cl1IndexPage.setContents(Utils.replacePackageName(c1Stream, plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ClientUiTest.java", "class"));
        
        //create client class
        FileResource<?> cl2IndexPage = (FileResource<?>) clDirectory.getChild("ErraiIocTestHelper.java");
        InputStream c2Stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/test/java/client/local/ErraiIocTestHelper.java.txt");
        cl2IndexPage.setContents(Utils.replacePackageName(c2Stream,plugin.getProject()));
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiIocTestHelper.java", "class"));
    }
    
    //TODO implement uninstall methods
    

	@Override
	void deleteWebappFiles(PipeOut pipeOut) {
        DirectoryResource webRoot = plugin.getProject().getFacet(WebResourceFacet.class).getWebRootDirectory();
        
        DirectoryResource wiDirectory = webRoot.getOrCreateChildDirectory("WEB-INF");
        wiDirectory.delete(true);
        
        FileResource<?> appPage = (FileResource<?>) webRoot.getChild("App.css");
        appPage.delete();

        FileResource<?> apphPage = (FileResource<?>) webRoot.getChild("App.html");
        apphPage.delete();
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
        appIndexPage.delete();
        
        FileResource<?> serviceIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiService.properties");
        serviceIndexPage.delete();
        
        FileResource<?> log4jIndexPage = (FileResource<?>) sourceRoot.getChild("log4j.properties");
        log4jIndexPage.delete();
	}

	@Override
	void deleteTestFiles(PipeOut pipeOut) {
        DirectoryResource resourceRoot = plugin.getProject().getFacet(ResourceFacet.class).getTestResourceFolder();
        
        FileResource<?> appIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiApp.properties");
        appIndexPage.delete();
        
        DirectoryResource javaRoot = plugin.getProject().getFacet(JavaSourceFacet.class).getTestSourceFolder();
        DirectoryResource clDirectory = javaRoot.getOrCreateChildDirectory("client");
        clDirectory.delete(true);
	}
    
}
