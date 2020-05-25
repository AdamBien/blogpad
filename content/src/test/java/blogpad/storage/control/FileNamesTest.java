/*
 */
package blogpad.storage.control;

import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class FileNamesTest {

    String title;

    @BeforeEach
    public void init() {
        this.title = "If You Get A Book, You Have To Start Reading--an airhacks.fm podcast episode";
    }

    @Test
    public void createFileName() {
        String fileName = FileNames.createFileName(this.title);
        assertNotNull(fileName);
        System.out.println("fileName = " + fileName);
        validate(fileName);
    }

    @Test
    public void encodeValidFileName() {
        validate(this.title);
    }

    @Test
    public void encodeInvalidFileName() {
        validate("*");
    }

    @Test
    public void prefix() {
        String fileName = FileNames.prefix();
        assertNotNull(fileName);
    }


    static void validate(String raw) {
        String fileName = FileNames.encode(raw);
        assertNotNull(fileName);
        System.out.println(fileName);

        File file = new File(fileName);
        boolean created = false;
        try {
            created = file.createNewFile();
            assertTrue(created);
            file.delete();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Invalid file name", ex);
        }
    }

}
