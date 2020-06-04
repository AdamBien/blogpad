
package blogpad.templates;

import java.io.UnsupportedEncodingException;
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
public class TemplatesResourceIT {

    private RestClientBuilder builder;
    private TemplatesResourceClient client;

    @Before
    public void init() {
        URI baseURI = URI.create("http://localhost:9080/content/resources");
        this.builder = RestClientBuilder.newBuilder().baseUri(baseURI);
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
        JsonObject template = Json.createObjectBuilder().
                add("fileName", name).
                add("content", content).
                build();
        return this.client.save(template);
    }


}
