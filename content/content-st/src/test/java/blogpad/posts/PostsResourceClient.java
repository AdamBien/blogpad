
package blogpad.posts;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;

/**
 *
 * @author airhacks.com
 */
@Path("posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PostsResourceClient {

    @GET
    public JsonObject recent(@QueryParam("max") @DefaultValue("-1") int max);

    default JsonObject all() {
        return this.recent(-1);
    }

    @PUT
    public Response update(JsonObject post);

    @POST
    public Response create(JsonObject post);

    @GET
    @Path("{title}")
    @ClientHeaderParam(name="Authorization", value="{getToken}")
    public Response getPostByTitle(@PathParam("title") String title);

    default String getToken() {
        return "Bearer eyJraWQiOiJqd3Qua2V5IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJkdWtlIiwidXBuIjoiZHVrZSIsImF1dGhfdGltZSI6MTU5NDk5NTA1MSwiaXNzIjoiYWlyaGFja3MiLCJncm91cHMiOlsicmVhZGVyIiwiYXV0aG9yIl0sImV4cCI6MjU5MzcxMjIwNywiaWF0IjoxNTk0OTk1MDUxLCJqdGkiOiI0MiJ9.b_ED-I9BVJvWJ5sCnfPq5H1V9-tTr3yg0UVuFbqICVazPp2BCq_aqHxETnTYPyXGivuQJMu1tHtckb2xepIKhYx0k8GEiUDljTCgc8IrO6tv4eGQHuTKBZYOchqW5Bt9MbTl16a2FcRwnvi6PfG6aFbIqExAGN1HWnLP0vDLLRulquGQRu1iK1D0gXsYT3l5UhK0AiIIdXo716TJc8J2GvfIZeUziYZ1o8hxb1aOXF19CTzcPJtaeyvzQzHLLWgwlO5U8q8q_AFC59MO_Wrn80L3X-lOpF5_tcMVVtxhMFVL9jlA_NlpFDGRL9d4RECHGnZIejeOsb2N5k8rlK-evw";
    }

}
