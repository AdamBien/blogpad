package blogpad.posts.boundary;

import blogpad.posts.entity.Post;
import blogpad.storage.control.BlogStore;
import java.net.URI;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostsResource {

    @Inject
    BlogStore store;

    @GET
    public JsonArray posts() {
        return null;
    }

    @POST
    public Response save(Post post) {
        String fileName = this.store.save(post);
        URI uri = URI.create(fileName);
        return Response.created(uri).build();
    }


}
