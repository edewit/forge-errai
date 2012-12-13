package org.jboss.errai.forge;

import java.io.InputStream;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.forge.enums.ErraiBusCommandsEnum;
import org.jboss.errai.forge.enums.ErraiFacetsEnum;
import org.jboss.errai.forge.enums.ErraiGeneratorCommandsEnum;
import org.jboss.errai.forge.enums.ErraiMarshalingCommandsEnum;
import org.jboss.errai.forge.enums.ErraiViaDefinitionEnum;
import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiBusFacet;
import org.jboss.errai.forge.facet.ErraiInstalled;
import org.jboss.errai.forge.gen.Generator;
import org.jboss.errai.forge.template.Velocity;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.ShellMessages;
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
@Alias("errai-cdi")
@RequiresProject
public class ErraiCDIPlugin extends AbstractPlugin implements Plugin {

    final Project project;
    final Event<InstallFacets> installFacets;
    
    private Velocity velocity;
    
    private Generator generator;
    
	public void setModuleInstalled(boolean isModuleInstalled) {
		ErraiInstalled.getInstance().setInstalled(isModuleInstalled);
	}

	@Inject
    public ErraiCDIPlugin(final Project project, final Event<InstallFacets> event) {
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
			if (!project.hasFacet(ErraiFacetsEnum.ERRAI_CDI.getFacet())) {
			     installFacets.fire(new InstallFacets(ErraiFacetsEnum.ERRAI_CDI.getFacet()));
			}
			if (project.hasFacet(ErraiFacetsEnum.ERRAI_CDI.getFacet())) {
				 ShellMessages.success(out, ErraiFacetsEnum.ERRAI_CDI + " is configured.");
			}
		}
    }

	// config	
	
	@Command("config")
    public void config(final PipeOut out) {
		// TODO implement cdi config here
    }

	//errai-cdi
	@Command("create-event-observer")
    public void errai(
    		@Option final ErraiBusCommandsEnum command,
    		@Option(name="portable") String portable, final PipeOut out) {
		
		//TODO implement
		
	}
	
		
}