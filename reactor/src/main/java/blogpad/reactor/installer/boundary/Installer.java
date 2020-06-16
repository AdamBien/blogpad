
package blogpad.reactor.installer.boundary;

import blogpad.reactor.installer.control.PostsStoreClient;
import blogpad.reactor.installer.control.InitialContent;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.RegistryType;
import org.eclipse.microprofile.rest.client.inject.RestClient;


/**
 *
 * @author airhacks.com
 */
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
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry registry;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object notUsed) {
        this.installTemplates();
    }

    public void installTemplates() {
        String singlePostTemplate = this.content.getSinglePostTemplate();
        Response saveResponse = this.client.saveTemplate(singlePostTemplateFileName, singlePostTemplate);
        registry.counter("installer_single_post_template_status_" + saveResponse.getStatus()).inc();

        String postListTemplate = this.content.getPostListTemplate();
        saveResponse = this.client.saveTemplate(postListTemplateFileName, postListTemplate);
        registry.counter("installer_post_list_template_status_" + saveResponse.getStatus()).inc();

        String firstPost = this.content.getFirstPost();
        saveResponse = this.client.savePost(firstPost);
        registry.counter("installer_initial_post_status_" + saveResponse.getStatus()).inc();

    }


}
