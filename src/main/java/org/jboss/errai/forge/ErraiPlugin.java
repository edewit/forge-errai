package org.jboss.errai.forge;

import org.jboss.errai.forge.enums.ErraiExamplesCommandsEnum;
import org.jboss.errai.forge.enums.ErraiFacetsEnum;
import org.jboss.errai.forge.enums.FacetsEnum;
import org.jboss.errai.forge.example.ErraiBusExample;
import org.jboss.errai.forge.example.ErraiCdiExample;
import org.jboss.errai.forge.example.ErraiJaxrsExample;
import org.jboss.errai.forge.example.ErraiUIExample;
import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiExampleFacet;
import org.jboss.errai.forge.facet.ErraiInstalled;
import org.jboss.errai.forge.gen.Generator;
import org.jboss.errai.forge.template.Velocity;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.ShellMessages;
import org.jboss.forge.shell.plugins.*;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * @author pslegr
 */
@Alias("errai")
@RequiresProject
public class ErraiPlugin extends AbstractPlugin implements Plugin {

    final Project project;
    final Event<InstallFacets> installFacets;

    private Velocity velocity;
    private Generator generator;
    
    //config
    private boolean default_portable = false;

    public void setModuleInstalled(boolean isModuleInstalled) {
		ErraiInstalled.getInstance().setInstalled(isModuleInstalled);
	}
	

	@Inject
    public ErraiPlugin(final Project project, final Event<InstallFacets> event) {
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
    public void setup(@Option final ErraiFacetsEnum module, final PipeOut out) {
		if (!project.hasFacet(module.getFacet())) {
		     installFacets.fire(new InstallFacets(module.getFacet()));
		}
		if (project.hasFacet(module.getFacet())) {
			 ShellMessages.success(out, module + " is configured.");
		}
    }

// config	
	
	@Command("config")
    public void setup(@Option(name="DEFAULT_PORTABLE") boolean default_portable, final PipeOut out) {
		this.default_portable = default_portable;
    }

//examples
	
	@Command("examples")
    public void errai_examples_setup(
    		@Option final ErraiExamplesCommandsEnum command, final PipeOut out) {
		
		//setup examples facet
		switch (command) {
		case ERRAI_EXAMPLES_SETUP:
			installFacets.fire(new InstallFacets(ErraiExampleFacet.class));
			return;
		}
		
		//check example facet installation
        if (!getProject().hasFacet(FacetsEnum.ERRAI_EXAMPLES.getFacet())) {
        	out.println("Errai Example Facet is not installed. Use 'errai examples setup' to get started.");
        	return;
        }
		
        //example commands options
		switch (command) {
			case ERRAI_EXAMPLES_HELP:
				//help
		        out.println(ShellColor.BLUE, "Use the install commands to install:");
		        out.println(ShellColor.BLUE, "'examples install-errai-bus': an example of simple Errai application");
		        out.println(ShellColor.BLUE, "'examples install-errai-cdi': an example of Errai CDI-based application");
		        out.println(ShellColor.BLUE, "'examples install-errai-jaxrs': an example of Errai Jaxrs applicatiton");
		        out.println(ShellColor.BLUE, "'examples install-errai-ui': an example of Errai UI applicatiton");
		        out.println(ShellColor.BLUE, "'examples uninstall-errai-bus': uninstall simple Errai Bus application");
		        out.println(ShellColor.BLUE, "'examples uninstall-errai-cdi': uninstal Errai CDI-based application");
		        out.println(ShellColor.BLUE, "'examples uninstall-errai-jaxrs': unistall Errai Jaxrs application");
		        out.println(ShellColor.BLUE, "'examples uninstall-errai-ui': unistall Errai UI application");
				break;
				
			case ERRAI_EXAMPLES_INSTALL_ERRAI_BUS:	
            	new ErraiBusExample(this, out).install();break;
			case ERRAI_EXAMPLES_INSTALL_ERRAI_CDI:
        		new ErraiCdiExample(this, out).install();break;
			case ERRAI_EXAMPLES_INSTALL_ERRAI_JAXRS:
	    		new ErraiJaxrsExample(this, out).install();break;
			case ERRAI_EXAMPLES_INSTALL_ERRAI_UI:
	    		new ErraiUIExample(this, out).install();break;
			case ERRAI_EXAMPLES_UNINSTALL_ERRAI_BUS:	
            	new ErraiBusExample(this, out).uninstall();break;
			case ERRAI_EXAMPLES_UNINSTALL_ERRAI_CDI:
        		new ErraiCdiExample(this, out).uninstall();break;
			case ERRAI_EXAMPLES_UNINSTALL_ERRAI_JAXRS:
	    		new ErraiJaxrsExample(this, out).uninstall();break;
			case ERRAI_EXAMPLES_UNINSTALL_ERRAI_UI:
	    		new ErraiUIExample(this, out).uninstall();break;
		}
    }
}