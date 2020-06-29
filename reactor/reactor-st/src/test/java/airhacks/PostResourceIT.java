/*
 */
package airhacks;

import java.net.URI;
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

    private PostsResourceClient client;

    @BeforeEach
    public void init() {
        var uri = Configuration.getURIValue("resource.uri");
        this.client = RestClientBuilder.
                newBuilder().
                baseUri(uri).
                build(PostsResourceClient.class);

    }

    @Test
    public void fetchRenderedSinglePost() {
        Response response = this.client.fetchPost("hello");
        int status = response.getStatus();
        assertEquals(200, status);
        String content = response.readEntity(String.class);
        assertNotNull(content);
        System.out.println("content = " + content);
    }

    @Test
    public void fetchRenderedRecentPosts() {
        Response response = this.client.fetchRecentPosts(10);
        int status = response.getStatus();
        assertEquals(200, status);
        String content = response.readEntity(String.class);
        assertNotNull(content);
        System.out.println("content = " + content);
    }


}
