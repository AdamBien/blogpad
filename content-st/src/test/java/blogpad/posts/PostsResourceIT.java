
package blogpad.posts;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class PostsResourceIT {

    private RestClientBuilder builder;
    private PostsResourceClient client;

    @BeforeEach
    public void init() {
        URI baseURI = URI.create("http://localhost:9080/content/resources");
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
    public void createSinglePost() {
        Response response = create("hello " + System.currentTimeMillis(), "world");
        assertEquals(response.getStatus(), 201);
        String location = response.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("location = " + location);
        JsonObject actual = fetchPost(location);
        assertNotNull(actual);
        System.out.println("actual = " + actual);
    }

    @Test
    public void createMultiplePostsWithSameTimes() {
        String title = "hello " + System.currentTimeMillis() + "-duplicate";

        Response response = create(title, "duplicate world");
        assertEquals(response.getStatus(), 201);
        String location = response.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("location = " + location);
        JsonObject actual = fetchPost(location);
        assertNotNull(actual);

        response = create(title, "duplicate world");
        assertEquals(response.getStatus(), 201);
        location = response.getHeaderString("Location");
        System.out.println("location = " + location);
        assertTrue(counterExists(location));
        System.out.println("location = " + location);
        actual = fetchPost(location);
        assertNotNull(actual);
        System.out.println("actual = " + actual);

    }

    boolean counterExists(String title) {
        var lastDashIndex = title.lastIndexOf("-");
        try {
            Integer.parseInt(title.substring(lastDashIndex));
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Test
    public void createAndUpdatePost() throws InterruptedException {
        String title = "createAndUpdatePost_" + System.currentTimeMillis();
        Response response = create(title, "world");
        assertEquals(response.getStatus(), 201);

        String location = response.getHeaderString("Location");
        assertNotNull(location);
        System.out.println("location = " + location);
        JsonObject actual = fetchPost(location);
        assertNotNull(actual);

        String createdAt = actual.getString("createdAt");
        assertNotNull(createdAt);

        String modifiedAt = actual.getString("modifiedAt", null);
        assertNull(modifiedAt);

        response = update(title, "world 2");
        location = response.getHeaderString("Content-Location");
        JsonObject updated = fetchPost(location);

        String createdAtAfterUpdate = updated.getString("createdAt");
        assertEquals(createdAt, createdAtAfterUpdate);

        modifiedAt = updated.getString("modifiedAt");
        assertNotNull(modifiedAt);

        System.out.println("modifiedAt = " + modifiedAt);
    }

    @Test
    public void getNonExistingTitle() {
        String title = "hello " + System.currentTimeMillis();
        Response response = this.client.getPostByTitle(title);
        assertEquals(204, response.getStatus());
    }

    @Test
    public void getExistingTitle() throws UnsupportedEncodingException {
        String title = "hello " + System.currentTimeMillis();
        this.create(title, "some content");

        Response response = this.client.getPostByTitle(URLEncoder.encode(title, "UTF-8"));
        assertEquals(response.getStatus(), 200);
    }

    public Response update(String title, String content) {
        JsonObject post = Json.createObjectBuilder().
                add("title", title).
                add("content", content).
                build();

        return this.client.update(post);
    }

    public Response create(String title, String content) {
        JsonObject post = Json.createObjectBuilder().
                add("title", title).
                add("content", content).
                build();

        return this.client.create(post);
    }

    static JsonObject fetchPostFromLocation(Response response) {
        String uri = response.getHeaderString("Location");
         return fetchPost(uri);
    }

    static JsonObject fetchPost(String uri) {
        GetClient client = RestClientBuilder.newBuilder().
                baseUri(URI.create(uri)).
                build(GetClient.class);
        return client.get();
    }

    static JsonObject createPost(String title, String content) {
        PostsResourceIT posts = new PostsResourceIT();
        posts.init();
        Response response = posts.create(title, content);
        return fetchPostFromLocation(response);
    }


}
