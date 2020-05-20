
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

    public String save(Post post) {
        String content = serialize(post);
        String title = post.title;
        String fileName = FileNames.encode(title);
        try {
            write(fileName, content);
        } catch (IOException ex) {
            throw new StorageException("Cannot store post " + post + " reason: " + ex.getMessage());
        }
        return fileName;

    }


    String serialize(Post post) {
        return this.jsonb.toJson(post);
    }

    void write(String fileName, String content) throws IOException {
        Path path = this.get(fileName);
        Files.writeString(path, content);
    }

    Path get(String path) {
        return this.storageFolder.resolve(path);
    }

    String read(String fileName) throws IOException {
        Path path = this.get(fileName);
        return Files.readString(path);
    }

}
