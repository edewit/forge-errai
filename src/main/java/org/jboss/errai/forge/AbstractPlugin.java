package org.jboss.errai.forge;

import org.jboss.errai.forge.enums.ErraiFacetsEnum;
import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiFacet;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.shell.plugins.PipeOut;

public abstract class AbstractPlugin {

	private Project project;
	public AbstractPlugin(Project project) {
		this.project = project;
	}

	//check given facet installation
	boolean isFacetInstalled(Class<? extends BaseFacet> facet,PipeOut out){
	    if (!project.hasFacet(facet)) {
	    	out.println(facet.getName() + " is not installed. Use 'setup' to get started.");
	    	return false;
	    }
	    return true;
    }

	//check project specific given facet installation
	boolean isProjectSpecificFacetInstalled(Class<? extends ErraiFacet> facet,PipeOut out){
	    if (!project.hasFacet(facet)) {
	    	out.println(facet.getName() + " is not installed. Use 'setup' to get started.");
	    	return false;
	    }
	    return true;
    }


	//check if any project specific facet is install and return
	Class<? extends ErraiFacet> getProjectSpecificAlreadyInstalledFacet(PipeOut out){
		ErraiFacetsEnum[] facets = ErraiFacetsEnum.values();
        for (ErraiFacetsEnum value : facets) {
            if (project.hasFacet(value.getFacet())) {
                out.println(value.getFacet().getName() + " is already installed. For multiple modules usage refer to 'errai setup multiple-modules'");
                return value.getFacet();
            }
        }
	    return null;
    }

}
