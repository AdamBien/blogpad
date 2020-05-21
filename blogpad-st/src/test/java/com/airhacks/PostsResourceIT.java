
package com.airhacks;

import java.net.MalformedURLException;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author airhacks.com
 */
public class PostsResourceIT {

    private RestClientBuilder builder;
    private PostsResourceClient client;

    @Before
    public void init() {
        URI baseURI = URI.create("http://localhost:9080/blogpad/resources");
        this.builder = RestClientBuilder.newBuilder().baseUri(baseURI);
        this.client = this.builder.build(PostsResourceClient.class);
    }

    @Test
    public void fetchAllPosts() throws MalformedURLException {
        assertNotNull(client);
        JsonArray result = client.all();
        assertNotNull(result);
        System.out.println("- " + result);
    }

    @Test
    public void createPost() {
        JsonObject post = Json.createObjectBuilder().
                add("title", "hello").
                add("content", "world").
                build();
        Response response = this.client.save(post);
        assertEquals(response.getStatus(), 201);
    }

}
