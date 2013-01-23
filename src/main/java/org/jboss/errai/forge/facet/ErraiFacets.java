package org.jboss.errai.forge.facet;


import org.jboss.errai.forge.*;

/**
 * @author pslegr
 */
public enum ErraiFacets {
    ERRAI_BUS_FACET("Errai Bus",ErraiBusFacet.class, ErraiBusExample.class),
    ERRAI_CDI_FACET("Errai CDI",ErraiCDIFacet.class, ErraiCdiExample.class),
    ERRAI_JAXRS_FACET("Errai Jaxrs",ErraiJaxrsFacet.class, ErraiJaxrsExample.class),
    ERRAI_UI_FACET("Errai UI",ErraiUIFacet.class, ErraiUIExample.class);
    		
    private String name;
    private Class<? extends ErraiBaseFacet> facet;
    private Class<? extends ErraiExample> example;

    private ErraiFacets(String name, Class<? extends ErraiBaseFacet> facet, Class<? extends ErraiExample> example) {
        this.name = name;
        this.facet = facet;
        this.example = example;
    }

    public Class<? extends ErraiBaseFacet> getFacet() {
        return facet;
    }

    public Class<? extends ErraiExample> getExample() {
        return example;
    }

    @Override
    public String toString() {
        return name;
    }
}
