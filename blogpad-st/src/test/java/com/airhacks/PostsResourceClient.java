
package com.airhacks;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author airhacks.com
 */
@Path("posts")
public interface PostsResourceClient {

    @GET
    public String posts();


}
