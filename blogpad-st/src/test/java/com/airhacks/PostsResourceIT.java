
package com.airhacks;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
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
        Response response = savePost("hello " + System.currentTimeMillis(), "world");
        assertEquals(response.getStatus(), 201);
    }

    @Test
    public void getNonExistingTitle() {
        String title = "hello " + System.currentTimeMillis();
        Response response = this.client.getPostByTitle(title);
        assertEquals(response.getStatus(), 204);
    }

    @Test
    public void getExistingTitle() throws UnsupportedEncodingException {
        String title = "hello " + System.currentTimeMillis();
        this.savePost(title, "some content");

        Response response = this.client.getPostByTitle(URLEncoder.encode(title, "UTF-8"));
        assertEquals(response.getStatus(), 200);
    }

    Response savePost(String title, String content) {
        JsonObject post = Json.createObjectBuilder().
                add("title", title).
                add("content", content).
                build();
        return this.client.save(post);
    }


}
