/*
 */
package blogpad.storage.control;

import blogpad.posts.entity.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String tempFolder = "target/temp" + System.currentTimeMillis();
        Path TEMP_PATH = Path.of(tempFolder);

        Files.createDirectories(TEMP_PATH);
        this.cut = new BlogStore();
        this.cut.folder = tempFolder;
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
        Path fileName = Path.of("test" + System.currentTimeMillis());
        String content = "{\"content\": \"duke is nice\",\"title\": \"firstpost\"}";

        this.cut.write(fileName, content);

        String actual = this.cut.readFromStorageFolder(fileName);
        assertEquals(actual, content);
    }

    @Test
    public void listFiles() throws IOException {
        String fileName = "firstpost " + System.currentTimeMillis();
        String content = "duke is nice";
        this.cut.save(new Post(fileName, content));

        List<Post> allFiles = this.cut.allFiles();
        System.out.println("allFiles = " + allFiles);
    }

}
