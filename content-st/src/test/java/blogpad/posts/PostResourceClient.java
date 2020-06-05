
package blogpad.posts;

import javax.json.JsonObject;
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
@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PostResourceClient {

    @GET
    @Path("{post}/comments")
    public Response comments(@PathParam("post") String title);

    @PUT
    @Path("{post}")
    public Response add(@PathParam("post") String post, JsonObject input);

}
