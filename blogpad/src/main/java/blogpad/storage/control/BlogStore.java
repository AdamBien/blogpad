
package blogpad.storage.control;

import blogpad.posts.entity.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author airhacks.com
 */
public class BlogStore {

    @Inject
    @ConfigProperty(name = "BLOG_STORE_FOLDER")
    String folder;

    private Jsonb jsonb;

    Path storageFolder;

    @PostConstruct
    public void init() {
        this.storageFolder = Path.of(this.folder);
        JsonbConfig config = new JsonbConfig().
                withFormatting(true);
        this.jsonb = JsonbBuilder.newBuilder().
                withConfig(config).
                build();
    }


    String serialize(Post post) {
        return this.jsonb.toJson(post);
    }

    void write(String fileName, String content) throws IOException {
        Path path = this.storageFolder.resolve(fileName);
        Files.writeString(path, content);

    }


}
