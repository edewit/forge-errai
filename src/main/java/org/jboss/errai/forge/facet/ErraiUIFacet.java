package org.jboss.errai.forge.facet;

import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.shell.plugins.RequiresFacet;

import java.util.Arrays;
import java.util.List;


@RequiresFacet({DependencyFacet.class, WebResourceFacet.class})
public class ErraiUIFacet extends ErraiBaseFacet {

    @Override
    void installErraiDeps() {
        String erraiVersion = Versions.getInstance().getErrai_version();
        String javaeeVersion = Versions.getInstance().getJavaee_version();

        List<? extends Dependency> dependencies = Arrays.asList(
                DependencyBuilder.create("org.jboss.errai:errai-javaee-all:" + erraiVersion),
                DependencyBuilder.create("org.jboss.spec:jboss-javaee-6.0:" + javaeeVersion).setScopeType("provided").setPackagingType("pom")
        );


        DependencyFacet deps = project.getFacet(DependencyFacet.class);
        for (Dependency dependency : dependencies) {
            deps.addDirectDependency(dependency);
        }

    }

    @Override
    protected void appendGwtModule() {
        appendGwtModule("org.jboss.errai.ui.UI");
        appendGwtModule("org.jboss.errai.databinding.DataBinding");
        appendGwtModule("org.jboss.errai.enterprise.CDI");

    }
}
