
package blogpad.posts.control;

import blogpad.posts.entity.Post;
import blogpad.posts.entity.Posts;
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
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

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
    @ConfigProperty(name = "archive.subdir", defaultValue = "archive")
    String archiveSubFolder;

    @Inject
    TitleNormalizer normalizer;

    private Jsonb jsonb;

    Path postsDirectory;
    Path archiveDirectory;

    @PostConstruct
    public void init() {
        Path storageFolder = Path.of(this.rootStorageDir);
        this.postsDirectory = storageFolder.resolve(this.postsSubFolder);
        this.archiveDirectory = storageFolder.resolve(this.archiveSubFolder);

        initializeStorageFolder(this.postsDirectory);
        initializeStorageFolder(this.archiveDirectory);

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

    public Posts allPosts() {
        return this.recentPosts(Integer.MAX_VALUE);
    }

    public Posts recentPosts(int max) {
        try {
            return Files.list(this.postsDirectory).
                    map(FileOperations::read).
                    map(this::deserialize).
                    sorted(this::newestFirst).
                    limit(max).
                    collect(Posts::new, Posts::addPost, Posts::add);
                    
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

    public void archive(Optional<Post> post) {
        post.ifPresent(this::archivePost);
    }

    void archivePost(Post post) {
        String title = post.title;
        Path postFile = this.postsDirectory.resolve(post.getFileName());
        Path titlePath = this.titleToPath(title);
        Path archivePath = this.archiveDirectory.resolve(titlePath);
        try {
            Files.move(postFile, archivePath);
        } catch (IOException ex) {
            throw new StorageException("Cannot archive post with title: " + title + " Reason: " + ex);
        }
    }

    Path titleToPath(String title) {
        String normalized = this.normalizer.normalize(title);
        return Path.of(normalized);
    }

    @Produces
    @Liveness
    public HealthCheck postsDirectoryCheck() {
        return () -> HealthCheckResponse.
                    named("posts-directory-exists").
                    state(Files.exists(this.postsDirectory)).
                    build();
    }
    @Produces
    @Liveness
    public HealthCheck archiveDirectoryCheck() {
        return () -> HealthCheckResponse.
                    named("archive-directory-exists").
                    state(Files.exists(this.archiveDirectory)).
                    build();
    }
}
