package org.jboss.errai.forge.example;

import java.io.InputStream;

import org.jboss.errai.forge.ErraiPlugin;
import org.jboss.errai.forge.Utils;
import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.plugins.PipeOut;

public class ErraiJaxrsExample extends AbstractExample{
	
	public ErraiJaxrsExample(final ErraiPlugin plugin, final PipeOut pipeOut) {
		super(plugin,pipeOut,ErraiExampleEnum.ERRAI_JAXRS_EXAMPLE);   }
	
    void createWebappFiles(final PipeOut pipeOut) {
        DirectoryResource webRoot = erraiExampleFacet.getWebRootDirectory();
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

    void createAppFiles(final PipeOut pipeOut) {
        DirectoryResource sourceRoot = erraiExampleFacet.getBasePackageDirectory();
        
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
    
    void createResourceFiles(final PipeOut pipeOut) {
        DirectoryResource sourceRoot = erraiExampleFacet.getResourceDirectory();
        
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
    
    void createTestFiles(final PipeOut pipeOut) {
        DirectoryResource resourceRoot = erraiExampleFacet.getTestResourceDirectory();
        
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) resourceRoot.getChild("ErraiApp.properties");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/test/resources/ErraiApp.properties");
        appIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiApp.properties", "file"));
        
        //create Test classes
        DirectoryResource javaRoot = erraiExampleFacet.getTestBasePackageDirectory();
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

	@Override
	void generatePOMFile(PipeOut pipeOut) {
        DirectoryResource exampleRoot = erraiExampleFacet.getExampleRootDirectory();
        //create pom file
        FileResource<?> appIndexPage = (FileResource<?>) exampleRoot.getChild("pom.xml");
        InputStream stream = ErraiPlugin.class.getResourceAsStream("/errai-jaxrs/pom.xml");
        appIndexPage.setContents(stream);
        pipeOut.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "pom.xml", "file"));
	}
}
