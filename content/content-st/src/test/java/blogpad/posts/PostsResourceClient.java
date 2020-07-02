
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
        return"""
                Bearer eyJraWQiOiJqd3Qua2V5IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.
                eyJzdWIiOiJkdWtlIiwidXBuIjoiZHVrZSIsImF1dGhfdGltZSI6MTU5MzcxMTIwNywiaXNzIjoiYWlyaGFja3MiLCJncm91cHMiOlsicmVhZGVyIiwiYXV0aG9yIl0sImV4cCI6MTU5MzcxMjIwNywiaWF0IjoxNTkzNzExMjA3LCJqdGkiOiI0MiJ9.
                awnjU853biIAqWLqa4mzMOm_2JffRM4g0WQ8iZylA8dDmOZTjGcR4l5R-u8I3vr0IhmS2I4s7OtCOKXByBM4aeCeiEyqNjXz516cDsYZ7x6AalHBA0uUQMDwFfzENaK885w2dwSC7DwC-Yhhx5FachdO0GxAt_2CNJCEtMaASm1UDgxvzz-rC6TgKAQsgV7-HwfnIuEHnYRm_PGWYuI4adXzyRcJQZF7jdRtmWbtMNUWSiEjB3HN2M-QJ_zAkY6kfdMm1mFQFz-UePLI3ZoLMOO_Anj2A1Vv8LoQx3oz1boQPS_a0XyjfmgPc3V5NsMkokLOVrGEwbhbRP0VivWxnQ        
                """;
    }

}
