/*
 */
package blogpad.storage.control;

import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class FileNamesTest {

    @Test
    public void validFileName() {
        validate("If You Get A Book, You Have To Start Reading--an airhacks.fm podcast episode");
    }

    @Test
    public void invalidFileName() {
        validate("*");
    }

    static void validate(String raw) {
        String fileName = FileNames.fileName(raw);
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
