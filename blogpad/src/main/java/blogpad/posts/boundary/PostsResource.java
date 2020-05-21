package blogpad.posts.boundary;

import blogpad.posts.entity.Post;
import blogpad.storage.control.BlogStore;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    public List<Post> posts() {
        return this.store.allFiles();
    }

    @GET
    @Path("${title}")
    public Post post(@PathParam("title") String title) {
        return this.store.getPost(title);
    }


    @PUT
    public Response save(Post post) {
        String fileName = this.store.save(post);
        URI uri = URI.create(fileName);
        return Response.created(uri).build();
    }


}
