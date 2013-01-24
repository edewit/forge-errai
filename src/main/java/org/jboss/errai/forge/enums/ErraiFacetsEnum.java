package org.jboss.errai.forge.enums;

import org.jboss.errai.forge.facet.*;


/**
* @author pslegr
*/
public enum ErraiFacetsEnum {
    ERRAI_BUS("errai-bus",ErraiBusFacet.class),
    ERRAI_CDI("errai-cdi",ErraiCDIFacet.class),
    ERRAI_UI("errai-ui",ErraiUIFacet.class),
    ERRAI_JAXRS("errai-jaxrs",ErraiJaxrsFacet.class),
    ERRAI_CORDOVA("errai-cordova", ErraiCordovaFacet.class);
    
    
    private String name;
    private Class<? extends ErraiFacet> facet;

    private ErraiFacetsEnum(String name, Class<? extends ErraiFacet> facet) {
        this.name = name;
        this.facet = facet;
    }

    public Class<? extends ErraiFacet> getFacet() {
        return facet;
    }

    @Override
    public String toString() {
        return name;
    }
}
