package org.jboss.errai.forge;

import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiFacets;
import org.jboss.errai.forge.facet.ErraiInstalled;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.ShellMessages;
import org.jboss.forge.shell.ShellPrompt;
import org.jboss.forge.shell.plugins.*;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.Arrays;

/**
 * @author pslegr
 */
@Alias("errai")
@RequiresProject
public class ErraiPlugin implements Plugin {

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
    public ErraiPlugin(final Project project, final Event<InstallFacets> event) {
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
        if (project.hasFacet(ErraiBaseFacet.class)) {
            out.println("Errai is installed.");
        } else {
            out.println("Errai is not installed. Use 'errai setup' to get started.");
        }
    }

    // confirmed working
    @Command("setup")
    public void setup(final PipeOut out) {
		ErraiFacets module = prompt.promptChoiceTyped("Which Errai module to install?",
        Arrays.asList(ErraiFacets.values()), ErraiFacets.ERRAI_BUS_FACET);
		
		if (!project.hasFacet(module.getFacet())) {
		     installFacets.fire(new InstallFacets(module.getFacet()));
		}
		if (project.hasFacet(module.getFacet())) {
			 ShellMessages.success(out, module + " Facet is configured.");
		}
		this.setModuleInstalled(false);		
		
		//TODO implement here logic for istalling only one facet at the time, once one facet is isntalled the others
		// won't be used 
		
    	
    }

    @Command("help")
    public void exampleDefaultCommand(@Option final String opt, final PipeOut pipeOut) {
        pipeOut.println(ShellColor.BLUE, "Use the install commands to install:");
        pipeOut.println(ShellColor.BLUE, "install-errai-bus: an example of simple Errai application");
        pipeOut.println(ShellColor.BLUE, "install-errai-cdi: an example of Errai CDI-based application");
        pipeOut.println(ShellColor.BLUE, "install-errai-jaxrs: an example of Errai Jaxrs applicatiton");
        pipeOut.println(ShellColor.BLUE, "install-errai-ui: an example of Errai UI applicatiton");
        pipeOut.println(ShellColor.BLUE, "uninstall-errai-bus: uninstall simple Errai Bus application");
        pipeOut.println(ShellColor.BLUE, "uninstall-errai-cdi: uninstal Errai CDI-based application");
        pipeOut.println(ShellColor.BLUE, "uninstall-errai-jaxrs: unistall Errai Jaxrs application");
        pipeOut.println(ShellColor.BLUE, "uninstall-errai-ui: unistall Errai UI application");
    }
    
    //install modules

    @Command("install-errai-bus")
    public void installErraiBus(final PipeOut pipeOut) {
        installFacet(pipeOut, ErraiFacets.ERRAI_BUS_FACET);
    }

    @Command("install-errai-cdi")
    public void installErraiCdi(final PipeOut pipeOut) {
        installFacet(pipeOut, ErraiFacets.ERRAI_CDI_FACET);
    }

    @Command("install-errai-jaxrs")
    public void installErraiJaxrs(final PipeOut pipeOut) {
        installFacet(pipeOut, ErraiFacets.ERRAI_JAXRS_FACET);
    }

    @Command("install-errai-ui")
    public void installErraiUI(final PipeOut pipeOut) {
        installFacet(pipeOut, ErraiFacets.ERRAI_UI_FACET);
    }
    
    @Command("uninstall-errai-bus")
    public void uninstallErraiBus(final PipeOut pipeOut) {
        uninstall(pipeOut, ErraiFacets.ERRAI_BUS_FACET);
    }

    @Command("uninstall-errai-cdi")
    public void uninstallErraiCdi(final PipeOut pipeOut) {
        uninstall(pipeOut, ErraiFacets.ERRAI_CDI_FACET);
    }
    
    @Command("uninstall-errai-jaxrs")
    public void uninstallErraiJaxrs(final PipeOut pipeOut) {
        uninstall(pipeOut, ErraiFacets.ERRAI_JAXRS_FACET);
    }
    
    @Command("uninstall-errai-ui")
    public void uninstallErraiUI(final PipeOut pipeOut) {
        uninstall(pipeOut, ErraiFacets.ERRAI_UI_FACET);
    }

    private void installFacet(PipeOut pipeOut, ErraiFacets facet) {
        toggleInstall(true, pipeOut, facet);
    }

    private void uninstall(PipeOut pipeOut, ErraiFacets facet) {
        toggleInstall(false, pipeOut, facet);
    }

    private void toggleInstall(boolean install, PipeOut pipeOut, ErraiFacets facet) {
        if (project.hasFacet(facet.getFacet())) {
            if (this.isModuleInstalled()) {
                ErraiExample erraiExample = newExampleInstaller(facet.getExample(), pipeOut);
                if (install) {
                    erraiExample.install();
                } else {
                    erraiExample.uninstall();
                }
            } else {
                pipeOut.println(facet + " is " + (install ? "" : "not") + "installed.");
            }
        } else {
            pipeOut.println(facet + " Facet is not installed. Use 'errai setup' to get started.");
        }
    }

    private ErraiExample newExampleInstaller(Class<? extends ErraiExample> facet, PipeOut pipeOut) {
        try {
            Constructor<?> constructor = facet.getConstructor(ErraiPlugin.class, PipeOut.class);
            return (ErraiExample) constructor.newInstance(this, pipeOut);
        } catch (Exception e) {
            throw new RuntimeException("could not instantiate example installer", e);
        }
    }
}