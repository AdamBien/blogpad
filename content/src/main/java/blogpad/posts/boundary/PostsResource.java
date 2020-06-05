package blogpad.posts.boundary;

import blogpad.posts.control.PostsStore;
import blogpad.posts.entity.Post;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostsResource {

    @Inject
    PostsStore store;

    @Context
    ResourceContext context;


    @GET
    public List<Post> posts(@QueryParam("max") @DefaultValue("-1") int max) {
        if (max == -1) {
            return this.store.allPosts();
        } else {
            return this.store.recentPosts(max);
        }
    }

    @Path("{title}")
    public PostResource post(@PathParam("title") String title) {
        return this.context.getResource(PostResource.class);
    }


    @PUT
    public Response save(Post post) {
        String fileName = this.store.save(post);
        URI uri = URI.create(fileName);
        return Response.created(uri).build();
    }


}
