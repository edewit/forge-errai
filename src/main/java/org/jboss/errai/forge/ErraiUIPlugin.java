package org.jboss.errai.forge;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.forge.enums.ErraiUICommandsEnum;
import org.jboss.errai.forge.facet.ErraiInstalled;
import org.jboss.errai.forge.facet.ErraiUIFacet;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
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

/**
 * @author pslegr
 */
@Alias("errai-ui")
@RequiresProject
public class ErraiUIPlugin extends AbstractPlugin implements Plugin {

    private final Project project;
    private final Event<InstallFacets> installFacets;

    @Inject
    private ShellPrompt prompt;


    public boolean isModuleInstalled() {
		return ErraiInstalled.getInstance().isInstalled();
	}

	public void setModuleInstalled(boolean isModuleInstalled) {
		ErraiInstalled.getInstance().setInstalled(isModuleInstalled);
	}

	@Inject
    public ErraiUIPlugin(final Project project, final Event<InstallFacets> event) {
		super(project);
        this.project = project;
        this.installFacets = event;
    }

    public Project getProject() {
		return project;
	}

	public Event<InstallFacets> getInstallFacets() {
		return installFacets;
	}

	@DefaultCommand
    public void status(final PipeOut out) {
        if (project.hasFacet(ErraiUIFacet.class)) {
            out.println("Errai-UI is installed.");
        } else {
            out.println("Errai-UI is not installed. Use 'errai-ui setup' to get started.");
        }
    }

    // confirmed working
    @Command("setup")
    public void setup(final PipeOut out) {
		if(getProjectSpecificAlreadyInstalledFacet(out) == null) {
	        if (!project.hasFacet(ErraiUIFacet.class)) {
	            installFacets.fire(new InstallFacets(ErraiUIFacet.class));
	        }
	        if (project.hasFacet(ErraiUIFacet.class)) {
	            ShellMessages.success(out, "ErraiUIFacet is configured.");
	        }
//			this.setModuleInstalled(false);
		}
    }
    
	//errai-ui commands
	@Command("command:")
    public void errai(
    		@Option final ErraiUICommandsEnum command,
    		@Option(name="from") String from, final PipeOut out) {
		
		//TODO implement
		
	}
    

    @Command("help")
    public void exampleDefaultCommand(@Option final String opt, final PipeOut pipeOut) {
        pipeOut.println(ShellColor.BLUE, "Use the following commands:");
        pipeOut.println(ShellColor.MAGENTA, "\t\tsetup: prepare the project for errai-ui");
        pipeOut.println(ShellColor.MAGENTA, "\t\tcomponent: create a CRUD interface of the giving JPA entity");
        //pipeOut.println(ShellColor.BLUE, "add-binding: prepare the project for errai-ui");
    }

}