
package blogpad.posts;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Path("posts/${title}")
public interface PostResourceClient {

    @Path("comments")
    public JsonArray comments();

    @PUT
    public Response add(JsonObject input);

}
