
package blogpad.posts.control;

import blogpad.posts.entity.Post;
import blogpad.storage.control.FileOperations;
import static blogpad.storage.control.FileOperations.initializeStorageFolder;
import static blogpad.storage.control.FileOperations.read;
import static blogpad.storage.control.FileOperations.write;
import blogpad.storage.control.StorageException;
import blogpad.tracing.boundary.Tracer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
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
public class PostsStore {

    @Inject
    @ConfigProperty(name = "max.collisions.title.count", defaultValue = "100")
    int maxCollisionsTitleCount;

    @Inject
    @ConfigProperty(name = "storage.dir")
    String rootStorageDir;

    @Inject
    @ConfigProperty(name = "posts.subdir", defaultValue = "posts")
    String postsSubFolder;

    @Inject
    TitleNormalizer normalizer;

    private Jsonb jsonb;

    Path postsDirectory;

    @PostConstruct
    public void init() {
        Path storageFolder = Path.of(this.rootStorageDir);
        this.postsDirectory = storageFolder.resolve(this.postsSubFolder);

        initializeStorageFolder(this.postsDirectory);

        JsonbConfig config = new JsonbConfig().
                withFormatting(true);
        this.jsonb = JsonbBuilder.newBuilder().
                withConfig(config).
                build();
    }

    public String create(Post newPost) {
        String title = newPost.title;
        Post updatedPost = newPost.setCreationDate();

        String content = serialize(updatedPost);
        String normalizedTitle = this.normalizer.normalize(title);
        String uniqueFileName = getUniqueFileName(normalizedTitle);
        write(this.postsDirectory, uniqueFileName, content);
        return uniqueFileName;
    }

    public String update(Post existing) {
        String title = existing.title;
        Post updatedPost = this.getPost(title).
                map(fetched -> Post.copy(existing, fetched)).
                orElseThrow(() -> new StorageException("Post with title " + title + " does not exist"));

        String content = serialize(updatedPost);
        String normalizedTitle = this.normalizer.normalize(title);
        String uniqueFileName = getUniqueFileName(normalizedTitle);
        write(this.postsDirectory, uniqueFileName, content);
        return uniqueFileName;
    }

    String getUniqueFileName(String title) {
        if (!postExists(title)) {
            return title;
        }
        for (int i = 1; i < maxCollisionsTitleCount + 1; i++) {
            String normalizedTitle = String.format("%s-%d", title, i);
            if (!postExists(normalizedTitle)) {
                return normalizedTitle;
            }
        }
        throw new StorageException("Max number of collisions: " + maxCollisionsTitleCount + " exceeded");
    }


    String serialize(Post post) {
        return this.jsonb.toJson(post);
    }

    Post deserialize(String content) {
        return this.jsonb.fromJson(content, Post.class);
    }

    String readFromStorageFolder(Path fileName) {
        Path path = this.postsDirectory.resolve(fileName);
        return read(path);
    }

    boolean postExists(String title) {
        return this.postExists(Path.of(title));
    }

    boolean postExists(Path title) {
        Path fileInStore = this.postsDirectory.resolve(title);
        return Files.exists(fileInStore);
    }

    public List<Post> allPosts() {
        return this.recentPosts(Integer.MAX_VALUE);
    }

    public List<Post> recentPosts(int max) {
        try {
            return Files.list(this.postsDirectory).
                    map(FileOperations::read).
                    map(this::deserialize).
                    sorted(this::newestFirst).
                    limit(max).
                    collect(Collectors.toList());
        } catch (IOException ex) {
            throw new StorageException("Cannot list contents of: " + this.postsDirectory + " reason: " + ex.getMessage());
        }
    }

    int newestFirst(Post first, Post next) {
        return next.createdAt.compareTo(first.createdAt);
    }

    public Optional<Post> getPost(String title) {
        String normalized = this.normalizer.normalize(title);        
        Path fileName = Path.of(normalized);
        return this.getPost(fileName).map(p -> p.withTitle(normalized));
    }

    public Optional<Post> getPost(Path title) {
        Tracer.info("Fetching: " + title);
        if (!postExists(title)) {
            Tracer.info("Post does not exist " + title);
            return Optional.empty();
        }
        String content = this.readFromStorageFolder(title);
        Tracer.info("Post found: " + content);
        return Optional.of(deserialize(content));
    }

}
