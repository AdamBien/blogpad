
package blogpad.installer.boundary;

import blogpad.installer.control.InitialContent;
import blogpad.installer.control.PostsStoreClient;
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
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry registry;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object notUsed) {
        this.installTemplates();
    }

    public void installTemplates() {
        String firstPost = this.content.getFirstPost();
        String content = this.content.getSinglePostTemplate();
        Response saveTemplateResponse = this.client.saveTemplate(singlePostTemplateFileName, content);
        registry.counter("installer_single_post_template_status_" + saveTemplateResponse.getStatus()).inc();
        Response savePostResponse = this.client.savePost(firstPost);
        registry.counter("installer_initial_post_status_" + savePostResponse.getStatus()).inc();

    }


}
