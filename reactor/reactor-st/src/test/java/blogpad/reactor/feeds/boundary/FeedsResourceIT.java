package blogpad.reactor.feeds.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import airhacks.Configuration;

public class FeedsResourceIT {

    private FeedsResourceClient client;

	@BeforeEach
    public void init() {
        var uri = Configuration.getURIValue("resource.uri");
        this.client = RestClientBuilder.newBuilder().baseUri(uri).build(FeedsResourceClient.class);
    }

    @Test
    public void fetchRSS() {
        Response response = this.client.rssFeed(-1);
        int status = response.getStatus();
        assertEquals(200, status);
    }

    @Test
    public void fetchAtom() {
        Response response = this.client.atomFeed(-1);
        int status = response.getStatus();
        assertEquals(200,status);
    }
    
}