package org.jboss.errai.forge;

import org.jboss.errai.forge.facet.ErraiBaseFacet;
import org.jboss.errai.forge.facet.ErraiCordovaFacet;
import org.jboss.forge.env.Configuration;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.shell.ShellPrompt;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;

import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * @author edewit@redhat.com
 */
@Alias("errai-cordova")
public class ErraiCordovaPlugin extends AbstractPlugin implements Plugin {

    private Configuration configuration;
    private Event<InstallFacets> installFacets;
    private ShellPrompt prompt;

    @Inject
    public ErraiCordovaPlugin(final Project project, final Configuration configuration,
                              final Event<InstallFacets> installFacets, final ShellPrompt prompt) {
        super(project);
        this.configuration = configuration;
        this.installFacets = installFacets;
        this.prompt = prompt;
    }


    @Command("add")
    public void addCordova(final PipeOut pipeOut) {
        if (isFacetInstalled(ErraiBaseFacet.class, pipeOut)) {
            String location = prompt.prompt("What is your external server location", "http://localhost:8080/");

            configuration.addProperty(ErraiCordovaFacet.URL, location);
            installFacets.fire(new InstallFacets(ErraiCordovaFacet.class));
        } else {
            pipeOut.println("Errai is not installed. Use 'errai setup' to get started.");
        }
    }
}
