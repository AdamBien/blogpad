
package blogpad.posts;

import java.net.MalformedURLException;
import java.net.URI;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blogpad.Configuration;

/**
 *
 * @author airhacks.com
 */
public class PostResourceIT {

    private RestClientBuilder builder;
    private PostResourceClient client;

    @BeforeEach
    public void init() {
        this.builder = RestClientBuilder.newBuilder().baseUri(Configuration.getURIValue("resource.uri"));
        this.client = this.builder.build(PostResourceClient.class);
    }

    @Test
    public void archivePost() {
        String title = "archival_test_" + System.currentTimeMillis();
        PostsResourceIT.createPost(title, "to be archived");
        Response response = this.client.archive(title);
        assertEquals(200, response.getStatus());
    }


    @Test
    public void listCommentsForNonExistingPost() throws MalformedURLException {
        Response result = client.comments("not-existing");
        assertEquals(204, result.getStatus());
    }

    @Test
    public void addCommentToExistingPost() {
        String title = "comment_test_" + System.currentTimeMillis();
        PostsResourceIT.createPost(title, "a post with comments");

        Response response = saveComment(title, "a new comment", "world", false);
        assertEquals(201, response.getStatus());
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
