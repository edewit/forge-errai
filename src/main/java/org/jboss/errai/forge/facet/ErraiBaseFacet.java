package org.jboss.errai.forge.facet;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.maven.model.Model;
import org.jboss.forge.maven.MavenCoreFacet;
import org.jboss.forge.maven.MavenPluginFacet;
import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.DependencyInstaller;
import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.RequiresFacet;

/**
 * @author pslegr
 */
@Alias("org.jboss.errai")
@RequiresFacet({ DependencyFacet.class, WebResourceFacet.class })
public abstract class ErraiBaseFacet extends BaseFacet
{
   public static final String SUCCESS_MSG_FMT = "***SUCCESS*** %s %s has been installed.";
   public static final String ALREADY_INSTALLED_MSG_FMT = "***INFO*** %s %s is already present.";

	@Inject
	public DependencyInstaller installer;
	
	abstract void installErraiFacetSpecifics();
	abstract boolean isFacetInstalled();

	public boolean install() {
		
	    if (!ErraiInstalled.getInstance().isInstalled()) {
			installBaseErraiDependencies();
			installGWTPlugin();
			installCleanPlugin();
			setDirectoryOutput();
	    }
	    else{
	    	ErraiInstalled.getInstance().setInstalled(true);
	    }
		installErraiFacetSpecifics();
		return true;
	}

	   @Override
	   public boolean isInstalled()
	   {
	      return isFacetInstalled();
	   }	
   /**
    * Install the maven dependencies required for Errai
    * 
    */
   private void installBaseErraiDependencies()
   {
	  String erraiVersion = Versions.getInstance().getErrai_version();
	  String gwtVersion= Versions.getInstance().getGwt_version();
	  String slf4jVersion= Versions.getInstance().getSlf4j_version();
	  
      List<? extends Dependency> dependencies = Arrays.asList(
              DependencyBuilder.create("org.jboss.errai:errai-bus:" + erraiVersion),
              DependencyBuilder.create("org.jboss.errai:errai-common:" + erraiVersion),
              DependencyBuilder.create("org.jboss.errai:errai-ioc:" + erraiVersion),
              DependencyBuilder.create("org.jboss.errai:errai-tools:" + erraiVersion),
              DependencyBuilder.create("com.google.gwt:gwt-servlet:" + gwtVersion),
              DependencyBuilder.create("com.google.gwt:gwt-user:" + gwtVersion),
              DependencyBuilder.create("org.slf4j:slf4j-log4j12:" + slf4jVersion)
      );

	   
      DependencyFacet deps = project.getFacet(DependencyFacet.class);
      for (Dependency dependency : dependencies) {
         deps.addDirectDependency(dependency);
      }
   }
   
   private void setDirectoryOutput(){
	    MavenCoreFacet mvnCoreFacet = project.getFacet(MavenCoreFacet.class);
	    Model model = mvnCoreFacet.getPOM();
	    model.getBuild().setOutputDirectory(Versions.getInstance().getOutput_dir());
	    mvnCoreFacet.setPOM(model);
	    
   }
   
   public void  installGWTPlugin() {
	   	ErraiGWTMavenPlugin erraiGWTPlugin = new ErraiGWTMavenPlugin();
	    MavenPluginFacet pluginFacet = project.getFacet(MavenPluginFacet.class);
	    if(! pluginFacet.hasPlugin(erraiGWTPlugin.getGwtDependencyBuilder())) {
	    	pluginFacet.addPlugin(erraiGWTPlugin.getGwtPlugin());
	    }
   }
   
   public void  installCleanPlugin() {
	   	ErraiCleanMavenPlugin erraiCleanPlugin = new ErraiCleanMavenPlugin();
	    MavenPluginFacet pluginFacet = project.getFacet(MavenPluginFacet.class);
	    if(! pluginFacet.hasPlugin(erraiCleanPlugin.getCleanDependencyBuilder())) {
	    	pluginFacet.addPlugin(erraiCleanPlugin.getCleanPlugin());
	    }
  }
}
