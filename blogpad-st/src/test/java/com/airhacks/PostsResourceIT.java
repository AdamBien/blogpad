
package com.airhacks;

import java.net.MalformedURLException;
import java.net.URI;
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

    @Before
    public void init() {
        URI baseURI = URI.create("http://localhost:9080/blogpad/resources");
        this.builder = RestClientBuilder.newBuilder().baseUri(baseURI);
    }

    @Test
    public void posts() throws MalformedURLException {
        PostsResourceClient client = this.builder.build(PostsResourceClient.class);
        assertNotNull(client);
        String result = client.posts();
        assertNotNull(result);
        System.out.println("- " + result);
    }
}
