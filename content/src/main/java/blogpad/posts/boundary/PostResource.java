
package blogpad.posts.boundary;

import blogpad.posts.control.PostsStore;
import blogpad.posts.entity.Comment;
import blogpad.posts.entity.Post;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    @Inject
    PostsStore store;

    @PathParam("title")
    String title;

    @GET
    @Path("comments")
    public List<Comment> comments() {
        Optional<Post> post = this.getWithCurrentTitle();
        return post.map(p -> p.comments).orElse(null);
    }

    @PUT
    public Response add(Comment comment) {
        Optional<Post> post = this.getWithCurrentTitle();

        if (post.isEmpty()) {
            return Response.
                    noContent().
                    build();
        }

        return post.map(p -> p.addComment(comment)).
                map(this.store::save).
                map(this::createdResponse).
                get();
    }

    public Response createdResponse(String fileName) {
        URI uri = URI.create(fileName);
        return Response.created(uri).build();
    }

    public Optional<Post> getWithCurrentTitle() {
        return this.store.
                getPost(title);
    }

    @GET
    public Post getPostOrNull() {
        return this.
                getWithCurrentTitle().
                orElse(null);
    }
}
