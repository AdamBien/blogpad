
package blogpad.storage.control;

import blogpad.posts.entity.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
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
            write(Path.of(fileName), content);
        } catch (IOException ex) {
            throw new StorageException("Cannot store post " + post + " reason: " + ex.getMessage());
        }
        return fileName;
    }


    String serialize(Post post) {
        return this.jsonb.toJson(post);
    }

    Post deserialize(String content) {
        return this.jsonb.fromJson(content, Post.class);
    }

    void write(Path fileName, String content) throws IOException {
        Path path = this.get(fileName);
        Files.writeString(path, content);
    }

    Path get(Path path) {
        return this.storageFolder.resolve(path);
    }

    String readFromStorageFolder(Path fileName) {
        Path path = this.get(fileName);
        return this.read(path);
    }

    String read(Path fileName) {
        try {
            return Files.readString(fileName);
        } catch (NoSuchFileException ex) {
            throw new StorageException("Cannot find file: " + fileName);
        } catch (IOException ex) {
            throw new StorageException("Cannot read file: " + fileName + " Reason: " + ex.toString());
        }

    }
    public List<Post> allFiles() {
        try {
            return Files.list(this.storageFolder).
                    map(this::read).
                    map(this::deserialize).
                    collect(Collectors.toList());
        } catch (IOException ex) {
            throw new StorageException("Cannot list contents of: " + this.storageFolder + " reason: " + ex.getMessage());
        }
    }

}
