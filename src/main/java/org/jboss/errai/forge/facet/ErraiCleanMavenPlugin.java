package org.jboss.errai.forge.facet;

import org.jboss.forge.maven.plugins.ConfigurationBuilder;
import org.jboss.forge.maven.plugins.ConfigurationElement;
import org.jboss.forge.maven.plugins.ConfigurationElementBuilder;
import org.jboss.forge.maven.plugins.ExecutionBuilder;
import org.jboss.forge.maven.plugins.MavenPluginBuilder;
import org.jboss.forge.project.dependencies.DependencyBuilder;

public class ErraiCleanMavenPlugin {
	
	private String cleanPluginVersion;
	private DependencyBuilder cleanDependencyBuilder;
	private ExecutionBuilder execution;
	private MavenPluginBuilder cleanPlugin;
	
	public ErraiCleanMavenPlugin() {
		    
		  String cleanVersion= Versions.getInstance().getMaven_clean_plugin_version();
		
		    cleanDependencyBuilder = DependencyBuilder.create()
				    .setArtifactId("maven-clean-plugin")
				    .setVersion(cleanVersion);
		    
		    ConfigurationElementBuilder include1 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText("www-test/**");
				    
		    ConfigurationElementBuilder include2 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText(".gwt/**");
				    
		    ConfigurationElementBuilder include3 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText(".errai/**");
		    
		    ConfigurationElementBuilder include4 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText("src/main/webapp/WEB-INF/deploy/**");
		    
		    ConfigurationElementBuilder include5 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText("src/main/webapp/WEB-INF/lib/**");
				    
		    ConfigurationElementBuilder include6 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText("src/main/webapp/App/**");
				    
		    ConfigurationElementBuilder include7 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText("**/gwt-unitCache/**");
		    
		    ConfigurationElementBuilder include8 = ConfigurationBuilder.create()
				    .createConfigurationElement("include")
				    .setText("**/*.JUnit/**");
		    
		    ConfigurationElementBuilder includes = ConfigurationBuilder.create()
				    .createConfigurationElement("includes")
		    		.addChild(include1)
		    		.addChild(include2)
		    		.addChild(include3)
		    		.addChild(include4)
		    		.addChild(include5)
		    		.addChild(include6)
		    		.addChild(include7)
		    		.addChild(include8);

		    
		    
		    ConfigurationElementBuilder directory = ConfigurationBuilder.create()
				    .createConfigurationElement("directory")
				    .setText("${basedir}");
		    
		    ConfigurationElementBuilder fileset = ConfigurationBuilder.create()
				    .createConfigurationElement("fileset")
				    .addChild(directory)
		    		.addChild(includes);
		    
		    ConfigurationElementBuilder filesets = ConfigurationBuilder.create()
				    .createConfigurationElement("filesets")
		    		.addChild(fileset);
		    
		    cleanPlugin = MavenPluginBuilder.create()
		    		.setDependency(cleanDependencyBuilder);
		    
		    cleanPlugin.getConfig().addConfigurationElement(filesets);
		   
	}

	public String getCleanPluginVersion() {
		return cleanPluginVersion;
	}

	public void setCleanPluginVersion(String cleanPluginVersion) {
		this.cleanPluginVersion = cleanPluginVersion;
	}

	public DependencyBuilder getCleanDependencyBuilder() {
		return cleanDependencyBuilder;
	}

	public void setCleanDependencyBuilder(DependencyBuilder cleanDependencyBuilder) {
		this.cleanDependencyBuilder = cleanDependencyBuilder;
	}

	public ExecutionBuilder getExecution() {
		return execution;
	}

	public void setExecution(ExecutionBuilder execution) {
		this.execution = execution;
	}

	public MavenPluginBuilder getCleanPlugin() {
		return cleanPlugin;
	}

	public void setCleanPlugin(MavenPluginBuilder cleanPlugin) {
		this.cleanPlugin = cleanPlugin;
	}

}
