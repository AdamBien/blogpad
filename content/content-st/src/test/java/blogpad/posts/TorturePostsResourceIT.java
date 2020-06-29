package blogpad.posts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blogpad.Configuration;

/**
 * TorturePostsResourceIT
 */
public class TorturePostsResourceIT {
    private PostsResourceClient client;
    private GetClient metrics;

    @BeforeEach
    public void init() {
        RestClientBuilder clientBuilder = RestClientBuilder.
                newBuilder().
                baseUri(Configuration.getURIValue("resource.uri"));
        this.client = clientBuilder.build(PostsResourceClient.class);


        RestClientBuilder metricsBuilder = RestClientBuilder.
                newBuilder().
                baseUri(Configuration.getURIValue("metrics.uri"));

        this.metrics = metricsBuilder.build(GetClient.class);

    }

    @Test
    public void startTorture() {
        assumeTrue(System.getProperty("torture",null)!=null);

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        List<CompletableFuture<Void>> futures = Stream.
                generate(()->this.createScenario(threadPool)).
                limit(500).
                collect(Collectors.toList());
        
        futures.forEach(CompletableFuture::join);
        this.verifyResults();
    }

    void verifyResults() {
        JsonObject results = this.metrics.get();
        System.out.println("results: " + results);
        assertNotNull(results);
        double oneMinRatePerSecond = results.getJsonObject("blogpad.posts.boundary.PostsResource.post").getJsonNumber("oneMinRate").doubleValue();
        System.out.println("oneMinRatePerSecond " + oneMinRatePerSecond);
        assertTrue(oneMinRatePerSecond > 2);

    }

    public CompletableFuture<Void> createScenario(ExecutorService threadPool) {
        return CompletableFuture.runAsync(this::getExistingTitle,threadPool).
        thenRunAsync(this::getNonExistingTitle, threadPool);

    }

    @Test
    public void getNonExistingTitle() {
        String title = "hello_" + System.currentTimeMillis();
        Response response = this.client.getPostByTitle(title);
        assertEquals(204, response.getStatus());
    }

    @Test
    public void getExistingTitle() {
        String title = "hello_" + System.nanoTime();
        this.create(title, "some content");

        Response response = this.client.getPostByTitle(title);
        JsonObject post = response.readEntity(JsonObject.class);
        assertNotNull(post);
    }

    public Response create(String title, String content) {
        JsonObject post = Json.createObjectBuilder().
                add("title", title).
                add("content", content).
                build();

        return this.client.create(post);
    }
}