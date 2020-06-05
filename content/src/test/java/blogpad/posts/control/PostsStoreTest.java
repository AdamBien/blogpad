/*
 */
package blogpad.posts.control;

import blogpad.posts.entity.Post;
import blogpad.storage.control.FileOperations;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class PostsStoreTest {

    PostsStore cut;

    @BeforeEach
    public void init() throws IOException {
        String tempFolder = "target/temp" + System.currentTimeMillis();
        Path TEMP_PATH = Path.of(tempFolder);

        Files.createDirectories(TEMP_PATH);
        this.cut = new PostsStore();
        this.cut.rootStorageDir = tempFolder;
        this.cut.postsSubFolder = "posts";
        this.cut.init();
    }

    @Test
    public void serialize() {
        Post post = new Post();
        post.content = "hello,world";
        post.title = "*the first post*";
        String serialized = this.cut.serialize(post);
        System.out.println(" " + serialized);
    }

    @Test
    public void listFiles() throws IOException {
        String fileName = "firstpost " + System.currentTimeMillis();
        String content = "duke is nice";
        this.cut.save(new Post(fileName, content));

        List<Post> allFiles = this.cut.allPosts();
        System.out.println("allFiles = " + allFiles);
    }

    @Test
    public void lastTwoFiles() throws IOException {
        for (int i = 0; i < 10; i++) {
            String fileName = i + "_" + System.currentTimeMillis();
            String content = "duke is nice " + i;
            this.cut.save(new Post(fileName, content));
        }

        List<Post> allFiles = this.cut.recentPosts(2);
        assertEquals(2, allFiles.size());
        System.out.println("lastFiles = " + allFiles);
        Post first = allFiles.get(0);
        Post second = allFiles.get(1);
        //reverse sorted
        assertTrue(first.title.startsWith("9"));
        assertTrue(second.title.startsWith("8"));
    }

    @Test
    public void notExistingPostCheck() {
        boolean result = this.cut.postExists(Path.of("not-existing" + System.currentTimeMillis()));
        assertFalse(result);
    }

    @Test
    public void existingPostCheck() throws IOException {
        String fileName = "test" + System.currentTimeMillis();
        String content = getPostAsJson();

        write(fileName, content);

        boolean result = this.cut.postExists(Path.of(fileName));
        assertTrue(result);
    }

    @Test
    public void getExistingPost() throws IOException {
        String fileName = "test " + System.currentTimeMillis();

        String content = getPostAsJson();
        write(fileName, content);

        Post post = this.cut.
                getPost(fileName).
                orElse(null);
        assertNotNull(post);

    }

    void write(String fileName, String content) {
        FileOperations.write(this.cut.postsDirectory, fileName, content);
    }

    @Test
    public void getNonExistingPost() {
        Post post = this.cut.getPost("-no-existing-" + System.currentTimeMillis()).
                orElse(null);
        assertNull(post);

    }

    String getPostAsJson() {
        return "{\"content\": \"duke is nice\",\"title\": \"firstpost\"}";
    }


}
