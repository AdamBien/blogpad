
package blogpad.templates.boundary;

import blogpad.templates.control.TemplatesStore;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("templates")
public class TemplatesResource {

    @Inject
    TemplatesStore store;


}
