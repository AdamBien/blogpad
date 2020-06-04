
package blogpad.templates.boundary;

import blogpad.templates.control.TemplatesStore;
import blogpad.templates.entity.Template;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
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
@Path("templates")
public class TemplatesResource {

    @Inject
    TemplatesStore store;

    @GET
    @Path("{name}")
    public Template post(@PathParam("name") String fileName) {
        return this.store.getTemplate(fileName);
    }


    @PUT
    public Response save(Template post) {
        String fileName = this.store.save(post);
        URI uri = URI.create(fileName);
        return Response.created(uri).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Template> templates() {
        return this.store.allTemplates();
    }



}
