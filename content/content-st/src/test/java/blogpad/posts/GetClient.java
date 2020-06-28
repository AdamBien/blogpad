
package blogpad.posts;

import javax.json.JsonObject;
import javax.ws.rs.GET;

/**
 *
 * @author airhacks.com
 */
public interface GetClient {

    @GET
    public JsonObject get();
}
