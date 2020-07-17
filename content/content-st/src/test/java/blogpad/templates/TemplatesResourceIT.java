
package blogpad.templates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.json.JsonArray;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blogpad.Configuration;

/**
 *
 * @author airhacks.com
 */
public class TemplatesResourceIT {

    private RestClientBuilder builder;
    private TemplatesResourceClient client;

    @BeforeEach
    public void init() {
        this.builder = RestClientBuilder.newBuilder().baseUri(Configuration.getURIValue("resource.uri"));
        this.client = this.builder.build(TemplatesResourceClient.class);
    }

    @Test
    public void fetchAllTemplates() throws MalformedURLException {
        assertNotNull(client);
        JsonArray result = client.all();
        assertNotNull(result);
        System.out.println("- " + result);
    }

    @Test
    public void createTemplate() {
        Response response = this.saveTemplate("hello_" + System.currentTimeMillis(), "world");
        assertEquals(response.getStatus(), 201);
    }

    @Test
    public void getNonExistingTemplate() {
        String name = "hello_" + System.currentTimeMillis();
        Response response = this.client.getTemplateByName(name);
        assertEquals(response.getStatus(), 204);
    }

    @Test
    public void getExistingTemplate() throws UnsupportedEncodingException {
        String name = "hello_" + System.currentTimeMillis();
        this.saveTemplate(name, "some content");

        Response response = this.client.getTemplateByName(name);
        assertEquals(response.getStatus(), 200);
    }

    Response saveTemplate(String name, String content) {
        return this.client.save(name, content);
    }


}
