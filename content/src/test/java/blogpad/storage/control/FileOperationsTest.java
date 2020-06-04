/*
 */
package blogpad.storage.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class FileOperationsTest {

    Path rootDirectory;

    @BeforeEach
    public void initialize() throws IOException {
        String tempFolder = "target/temp" + System.currentTimeMillis();
        rootDirectory = Path.of(tempFolder);
        Files.createDirectories(rootDirectory);

    }


    @Test
    public void writeThenReadFile() throws IOException {
        String fileName = "test" + System.currentTimeMillis();
        String content = "{\"content\": \"duke is nice\",\"title\": \"firstpost\"}";

        FileOperations.write(rootDirectory, fileName, content);

        String actual = FileOperations.read(rootDirectory.resolve(fileName));
        assertEquals(actual, content);
    }

}
