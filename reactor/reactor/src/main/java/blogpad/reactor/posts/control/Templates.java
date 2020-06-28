
package blogpad.reactor.posts.control;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author airhacks.com
 */
@Path("templates")
@RegisterRestClient(configKey = "content")
@Produces(MediaType.APPLICATION_JSON)
public interface Templates {

    @GET
    @Path("{name}")
    public JsonObject getTemplateByName(@PathParam("name") String name);


}
