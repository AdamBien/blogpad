package blogpad.posts.boundary;

import blogpad.posts.control.PostsStore;
import blogpad.posts.entity.Post;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostsResource {

    @Inject
    PostsStore store;

    @Context
    ResourceContext context;

    @Inject
    PostResource postResource;


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
        return this.context.initResource(postResource);
    }


    @PUT
    public Response update(@Context UriInfo info, Post post) {
        String fileName = this.store.update(post);
        URI uri = info.
                getAbsolutePathBuilder().
                path(fileName).
                build();
        return Response.noContent().header("Content-Location", uri.toString()).build();
    }

    @POST
    public Response created(@Context UriInfo info, Post post) {
        String fileName = this.store.create(post);
        URI uri = info.
                getAbsolutePathBuilder().
                path(fileName).
                build();
        return Response.created(uri).build();
    }


}
