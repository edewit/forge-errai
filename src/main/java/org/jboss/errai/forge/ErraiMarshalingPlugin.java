package org.jboss.errai.forge;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.errai.forge.enums.ErraiFacetsEnum;
import org.jboss.errai.forge.enums.ErraiGeneratorCommandsEnum;
import org.jboss.errai.forge.enums.ErraiMarshalingCommandsEnum;
import org.jboss.errai.forge.enums.ErraiViaDefinitionEnum;
import org.jboss.errai.forge.facet.ErraiInstalled;
import org.jboss.errai.forge.gen.Generator;
import org.jboss.errai.forge.template.Velocity;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
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
@Alias("errai-marshaling")
@RequiresProject
public class ErraiMarshalingPlugin implements Plugin {

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
    public ErraiMarshalingPlugin(final Project project, final Event<InstallFacets> event) {
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
	
	// config	
	@DefaultCommand
	@Command("set-portable")
    public void setup(@Option(name="DEFAULT_PORTABLE") boolean default_portable, final PipeOut out) {
		this.default_portable = default_portable;
    }

	//errai-marshaling
	@Command("command:")
    public void errai_marshaling_setup(
    		@Option final ErraiMarshalingCommandsEnum command,
    		@Option(name="from", required=true) String from, 
    		@Option(name="recursive", defaultValue="false", required=true) boolean recursive,
    		@Option(name="via", defaultValue="ANNOTATION", required=true) final ErraiViaDefinitionEnum via, final PipeOut out) {
		
		//check facet installation
		if(!this.isFacetInstalled(out))
			return;
		
        // commands
		if(command.equals(ErraiMarshalingCommandsEnum.ERRAI_MARSHALING_SET_PORTABLE)){
			// generate @Portable for defined classes
			if(via.toString().equals(ErraiViaDefinitionEnum.ANNOTATION.toString())) {
				//use annotation inside classes
				if(recursive == true) {
					generator.generate(ErraiGeneratorCommandsEnum.ERRAI_MARSHALING_SET_PORTABLE_RECURSIVE, from);
				}
				else {
					generator.generate(ErraiGeneratorCommandsEnum.ERRAI_MARSHALING_SET_PORTABLE, from);				
				}
			}
			else {
				// use definitions inside ErraiApp.properties
				if(recursive == true) {
					generator.generate(ErraiGeneratorCommandsEnum.ERRAI_MARSHALING_SET_PORTABLE_RECURSIVE_VIA_CONFIG, from);
				}
				else {
					generator.generate(ErraiGeneratorCommandsEnum.ERRAI_MARSHALING_SET_PORTABLE_VIA_CONFIG, from);				
				}
			}
		}
		if(command.equals(ErraiMarshalingCommandsEnum.ERRAI_MARSHALING_IMMUTABLE_BUILDER)){
			if(recursive == true) {
				generator.generate(ErraiGeneratorCommandsEnum.ERRAI_MARSHALING_IMMUTABLE_BUILDER_RECURSIVE, from);
			}
			else {
				generator.generate(ErraiGeneratorCommandsEnum.ERRAI_MARSHALING_IMMUTABLE_BUILDER, from);				
			}
		}
    }

	//check any of facets installation
    private boolean isFacetInstalled(PipeOut out){
    	//TODO here mush be implementation to check if any of allowed facets are installed
    	// Marshaling plugin can be used either bur or cdi or ui Facets are installed, so we must
    	// iterate all over these (which are allowed, some might be restricted)
	    if (!getProject().hasFacet(ErraiFacetsEnum.ERRAI_BUS.getFacet())) {
	    	out.println("Requires some Facet to be installed. Use 'errai setup' to get started.");
	    	return false;
	    }
	    return true;
    }
	
	

	
}