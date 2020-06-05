
package blogpad.posts;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PostsResourceClient {

    @GET
    public JsonArray recent(@QueryParam("max") @DefaultValue("-1") int max);

    default JsonArray all() {
        return this.recent(-1);
    }

    @PUT
    public Response save(JsonObject post);

    @GET
    @Path("{title}")
    public Response getPostByTitle(@PathParam("title") String title);

}
