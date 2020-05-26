package blogpad.reactor.posts.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author airhacks.com
 */
@Path("posts")
public class PostsResource {

    @Inject
    Reactor reactor;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{title}")
    public String post(@PathParam("title") String title) {
        return this.reactor.react(title);
    }

}
