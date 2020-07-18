
package blogpad.reactor.installer.boundary;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import blogpad.logging.boundary.Blogger;
import blogpad.reactor.installer.control.InitialContent;
import blogpad.reactor.installer.control.PostsStoreClient;
import io.quarkus.runtime.Startup;


/**
 *
 * @author airhacks.com
 */
@Startup
@ApplicationScoped
public class Installer {

    @Inject
    @RestClient
    PostsStoreClient client;

    @Inject
    InitialContent content;

    @Inject
    @ConfigProperty(name = "single.post.template")
    String singlePostTemplateFileName;

    @Inject
    @ConfigProperty(name = "post.list.template")
    String postListTemplateFileName;

    @Inject
    @ConfigProperty(name = "rss.feed.template")
    String rssFeedTemplateFileName;

    @Inject
    @ConfigProperty(name = "atom.feed.template")
    String atomFeedTemplateFileName;

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry registry;

    @Inject
    Blogger LOG;

    @PostConstruct
    public void installTemplates() {
        LOG.info("initializing");
        String singlePostTemplate = this.content.getSinglePostTemplate();
        Response saveResponse = this.client.saveTemplate(singlePostTemplateFileName, singlePostTemplate);
        registry.counter("installer_single_post_template_status_" + saveResponse.getStatus()).inc();

        String postListTemplate = this.content.getPostListTemplate();
        saveResponse = this.client.saveTemplate(postListTemplateFileName, postListTemplate);
        registry.counter("installer_post_list_template_status_" + saveResponse.getStatus()).inc();

        String atomFeedTemplate = this.content.getAtomFeedTemplate();
        this.client.saveTemplate(atomFeedTemplateFileName, atomFeedTemplate);
        registry.counter("installer_atom_feed_template_status_" + saveResponse.getStatus()).inc();

        String rssFeedTemplate = this.content.getRssFeedTemplate();
        this.client.saveTemplate(rssFeedTemplateFileName, rssFeedTemplate);
        registry.counter("installer_rss_feed_template_status_" + saveResponse.getStatus()).inc();

        String firstPost = this.content.getFirstPost();
        saveResponse = this.client.savePost(firstPost);
        registry.counter("installer_initial_post_status_" + saveResponse.getStatus()).inc();

    }


}
