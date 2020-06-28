package blogpad.reactor.posts.boundary;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.opentracing.Traced;

/**
 *
 * @author airhacks.com
 */
@Path("posts")
public class PostsResource {

    @Inject
    Reactor reactor;

    @GET
    @Traced
    @Produces(MediaType.TEXT_HTML)
    @Path("{title}")
    public String fetchPostByTitle(@PathParam("title") String title) {
        return this.reactor.react(title);
    }

    @GET
    @Traced
    @Produces(MediaType.TEXT_HTML)
    public String fetchRecentPosts(@QueryParam("max") @DefaultValue("-1") int max) {
        return this.reactor.react(max);
    }

}
