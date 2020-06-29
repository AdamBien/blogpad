
package blogpad.reactor.posts.control;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author airhacks.com
 */
@RegisterRestClient(configKey = "content")
@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PostsResourceClient {

    @GET
    @Path("{title}")
    public String getPostByTitle(@PathParam("title") String title);

    @GET
    public String recentPosts(@QueryParam("max") @DefaultValue("-1") int max);

}
