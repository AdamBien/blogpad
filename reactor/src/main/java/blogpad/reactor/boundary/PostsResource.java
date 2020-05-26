package blogpad.reactor.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author airhacks.com
 */
@Path("articles")
public class PostsResource {

    @Inject
    Reactor reactor;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{title}")
    public String post(String title) {
        return this.reactor.react(title);
    }

}
