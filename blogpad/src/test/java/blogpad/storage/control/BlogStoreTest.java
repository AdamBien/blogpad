/*
 */
package blogpad.storage.control;

import blogpad.posts.entity.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class BlogStoreTest {

    BlogStore cut;

    @BeforeEach
    public void init() throws IOException {
        Files.createDirectories(Path.of("src/test/temp"));
        this.cut = new BlogStore();
        this.cut.folder = "src/test/temp";
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
    public void writeFile() throws IOException {
        String fileName = "firstpost";
        String content = "duke is nice";
        this.cut.write(fileName, content);
    }




}
