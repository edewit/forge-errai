package org.jboss.errai.forge;

import java.io.InputStream;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.forge.enums.ErraiFacetsEnum;
import org.jboss.errai.forge.enums.ErraiGeneratorCommandsEnum;
import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiInstalled;
import org.jboss.errai.forge.gen.Generator;
import org.jboss.errai.forge.template.Velocity;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.ShellMessages;
import org.jboss.forge.shell.ShellPrompt;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.DefaultCommand;
import org.jboss.forge.shell.plugins.Option;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresProject;
import org.jboss.forge.shell.plugins.SetupCommand;

/**
 * @author pslegr
 */
@Alias("errai-bus")
@RequiresProject
public class ErraiBusPlugin extends AbstractPlugin implements Plugin {

    final Project project;
    final Event<InstallFacets> installFacets;
    
    @Inject
    private ShellPrompt prompt;
    
    private Velocity velocity;
    
    private Generator generator;
    
    //config
    private boolean default_portable = false;
    
	public void setModuleInstalled(boolean isModuleInstalled) {
		ErraiInstalled.getInstance().setInstalled(isModuleInstalled);
	}
	

	@Inject
    public ErraiBusPlugin(final Project project, final Event<InstallFacets> event) {
		super(project);
        this.project = project;
        this.installFacets = event;
        this.velocity = new Velocity(this.getProject());
        this.generator = new Generator(project, velocity);
        
    }

    public Project getProject() {
		return project;
	}

	public Event<InstallFacets> getInstallFacets() {
		return installFacets;
	}

	@DefaultCommand
    public void status(final PipeOut out) {
        if (project.hasFacet(ErraiBaseFacet.class)) {
            out.println("Errai is installed.");
        } else {
            out.println("Errai is not installed. Use 'errai setup' to get started.");
        }
    }

	@SetupCommand
	@Command("setup")
    public void setup(final PipeOut out) {
		if(getProjectSpecificAlreadyInstalledFacet(out) == null) {
			if (!project.hasFacet(ErraiFacetsEnum.ERRAI_BUS.getFacet())) {
			     installFacets.fire(new InstallFacets(ErraiFacetsEnum.ERRAI_BUS.getFacet()));
			}
			if (project.hasFacet(ErraiFacetsEnum.ERRAI_BUS.getFacet())) {
				 ShellMessages.success(out, ErraiFacetsEnum.ERRAI_BUS + " is configured.");
			}
		}
    }

	//errai-bus
	
	@Command("setup-Errai.properties")
	public void bus_setup_props(final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		DirectoryResource projectRoot = project.getProjectRoot();				
        DirectoryResource sourceRoot = projectRoot.getOrCreateChildDirectory("src").
        		getOrCreateChildDirectory("main").getOrCreateChildDirectory("resources");
        //create App props
        FileResource<?> appIndexPage = (FileResource<?>) sourceRoot.getChild("ErraiApp.properties");
        InputStream stream = ErraiBusPlugin.class.getResourceAsStream("/errai-bus/resources/ErraiApp.properties");
        appIndexPage.setContents(stream);
        out.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "ErraiApp.properties", "file"));
	}
	
	@Command("setup-log4j.properties")
	public void bus_setup_log(final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		DirectoryResource projectRoot = project.getProjectRoot();				
        DirectoryResource sourceRoot = projectRoot.getOrCreateChildDirectory("src").
        		getOrCreateChildDirectory("main").getOrCreateChildDirectory("resources");
        projectRoot = project.getProjectRoot();				
        sourceRoot = projectRoot.getOrCreateChildDirectory("src").
        		getOrCreateChildDirectory("main").getOrCreateChildDirectory("resources");
        //create log4j props
        FileResource<?> log4jIndexPage = (FileResource<?>) sourceRoot.getChild("log4j.properties");
        InputStream stream = ErraiBusPlugin.class.getResourceAsStream("/errai-bus/resources/log4j.properties");
        log4jIndexPage.setContents(stream);
        out.println(ShellColor.YELLOW, String.format(ErraiBaseFacet.SUCCESS_MSG_FMT, "log4j.properties", "file"));
	}
	
	@Command("rpc-generate-empty-service-impl")
	public void bus_rpc_generate_empty_service_impl(final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		// @Service Impl empty template generation
		velocity.createJavaSourceWithTemplateName("YourEmptyServiceImpl.java.vm");
	}
	
	@Command("rpc-generate-simple-service-impl")
	public void bus_rpc_generate_simple_service_impl(final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		// @Service Impl empty simple class generation
		velocity.createJavaSourceWithTemplateName("SimpleServiceImpl.java.vm");
	}
	
	@Command("rpc-generate-remotes-for-all-services")
	public void bus_rpc_generate_remotes_for_all_services(final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		// generate @Remote interfaces for all @Service classes
		generator.generate(ErraiGeneratorCommandsEnum.ERRAI_BUS_GENERATE_REMOTES_FROM_ALL_SERVICE_CLASSES);
	}
	
	@Command("rpc-generate-remote-from-service")
	public void bus_rpc_generate_remote_from_service(@Option(name="from") String from, final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		// generate @Remote interface for given @Service class
        System.out.println("from: " + from);
		if(from != null){
			generator.generate(ErraiGeneratorCommandsEnum.ERRAI_BUS_GENERATE_REMOTE_FROM_SERVICE_CLASS,from);
		}
	}
	
	@Command("rpc-invoke-endpoint")
	public void bus_rpc_invoke_endpoint(final PipeOut out){
		if(!this.isProjectSpecificFacetInstalled(ErraiFacetsEnum.ERRAI_BUS.getFacet(),out))
			return;
		// do rpc invoke
		// TODO generate here service call with optional success/error callbacks
        out.println(ShellColor.BLUE, "do rpc invoke:");
	}
}