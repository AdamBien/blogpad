
package blogpad.posts.boundary;

import blogpad.posts.control.PostsStore;
import blogpad.posts.entity.Comment;
import blogpad.posts.entity.Post;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
public class PostResource {

    @Inject
    PostsStore store;

    @PathParam("title")
    String title;


    @Path("comments")
    public Optional<List<Comment>> comments() {
        Optional<Post> post = this.get();
        return post.map(p -> p.comments);
    }

    @PUT
    public Response add(Comment comment) {
        Optional<Post> post = this.get();
        if (post.isEmpty()) {
            return Response.noContent().build();
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

    @GET
    public Optional<Post> get() {
        return this.store.getPost(title);
    }
}
