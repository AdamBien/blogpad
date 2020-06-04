
package blogpad.storage.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 *
 * @author airhacks.com
 */
public interface FileOperations {

    public static void initializeStorageFolder(Path path) {

        if (Files.exists(path)) {
            if (!Files.isDirectory(path)) {
                throw new StorageException("Path " + path + " is not a directory");
            }
        } else {
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                throw new StorageException("Cannot create directory: " + path);
            }
        }
    }

    public static String read(Path fileName) {
        try {
            return Files.readString(fileName);
        } catch (NoSuchFileException ex) {
            throw new StorageException("Cannot find file: " + fileName);
        } catch (IOException ex) {
            throw new StorageException("Cannot read file: " + fileName + " Reason: " + ex.toString());
        }
    }

    public static void write(Path directory, String fileName, String content) {
        try {
            Path path = directory.resolve(fileName);
            Files.writeString(path, content);
        } catch (IOException ex) {
            throw new StorageException("Cannot write content to: " + directory + " -> " + fileName);
        }
    }




}
