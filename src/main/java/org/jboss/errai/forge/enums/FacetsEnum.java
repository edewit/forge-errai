package org.jboss.errai.forge.enums;

import org.jboss.errai.forge.facet.ErraiExampleFacet;
import org.jboss.forge.project.facets.BaseFacet;



/**
 * @author pslegr
 */
public enum FacetsEnum {
    ERRAI_EXAMPLES("examples",ErraiExampleFacet.class);
    		
    private String name;
    private Class<? extends BaseFacet> facet;

    private FacetsEnum(String name, Class<? extends BaseFacet> facet) {
        this.name = name;
        this.facet = facet;
    }

    public Class<? extends BaseFacet> getFacet() {
        return facet;
    }

    @Override
    public String toString() {
        return name;
    }
}
