package org.jboss.errai.forge.facet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Versions {
	
	private String errai_version;
	private String gwt_version;
	private String javaee_version;
	private String slf4j_version;
	private String maven_clean_plugin_version;
	
	private static Versions instance;
	
	private Versions() {
		Properties configProp = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/org/jboss/errai/forge/facet/versions.properties");
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
		this.errai_version = configProp.getProperty("errai.version");
		this.gwt_version = configProp.getProperty("gwt.version");
		this.javaee_version = configProp.getProperty("javaee.version");
		this.slf4j_version = configProp.getProperty("slf4j.version");
		this.maven_clean_plugin_version = configProp.getProperty("maven.clean.plugin.version");
	}

	public static Versions getInstance(){
		if(instance == null)
			instance = new Versions();
		return instance;
	}
	
	public String getErrai_version() {
		return errai_version;
	}

	public String getGwt_version() {
		return gwt_version;
	}

	public String getJavaee_version() {
		return javaee_version;
	}

	public String getSlf4j_version() {
		return slf4j_version;
	}

	public String getMaven_clean_plugin_version() {
		return maven_clean_plugin_version;
	}
}
