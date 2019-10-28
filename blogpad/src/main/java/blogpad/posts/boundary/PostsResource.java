package blogpad.posts.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("posts")
public class PostsResource {

    @GET
    public String posts() {
        return "posts";
    }

}
