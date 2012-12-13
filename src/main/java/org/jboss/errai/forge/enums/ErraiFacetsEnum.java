package org.jboss.errai.forge.enums;

import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiBusFacet;
import org.jboss.errai.forge.facet.ErraiCDIFacet;
import org.jboss.errai.forge.facet.ErraiExampleFacet;
import org.jboss.errai.forge.facet.ErraiJaxrsFacet;
import org.jboss.errai.forge.facet.ErraiUIFacet;



/**
 * @author pslegr
 */
public enum ErraiFacetsEnum {
    ERRAI_BUS("errai-bus",ErraiBusFacet.class),
    ERRAI_CDI("errai-cdi",ErraiCDIFacet.class),
    ERRAI_UI("errai-ui",ErraiUIFacet.class),
    ERRAI_JAXRS("errai-jaxrs",ErraiJaxrsFacet.class);
    
    
    private String name;
    private Class<? extends ErraiBaseFacet> facet;

    private ErraiFacetsEnum(String name, Class<? extends ErraiBaseFacet> facet) {
        this.name = name;
        this.facet = facet;
    }

    public Class<? extends ErraiBaseFacet> getFacet() {
        return facet;
    }

    @Override
    public String toString() {
        return name;
    }
}
