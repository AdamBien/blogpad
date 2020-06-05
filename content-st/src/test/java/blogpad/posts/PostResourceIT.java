
package blogpad.posts;

import java.net.MalformedURLException;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class PostResourceIT {

    private RestClientBuilder builder;
    private PostResourceClient client;

    @BeforeEach
    public void init() {
        URI baseURI = URI.create("http://localhost:9080/content/resources");
        this.builder = RestClientBuilder.newBuilder().baseUri(baseURI);
        this.client = this.builder.build(PostResourceClient.class);
    }

    @Test
    public void listComments() throws MalformedURLException {
        JsonArray result = client.comments("no-existing");
        assertNotNull(result);
    }

    @Test
    public void addCommentToExistingPost() {
        JsonObject post = PostsResourceIT.createPost("comment-test", "a post with comments");
        Response response = saveComment(post.getString("title"), "a new comment", "world", false);
        assertEquals(response.getStatus(), 201);
    }

    Response saveComment(String postTitle, String name, String content, boolean approved) {
        JsonObject post = Json.createObjectBuilder().
                add("name", name).
                add("content", content).
                add("approved", approved).
                build();
        return this.client.add(postTitle, post);
    }

}
