
package blogpad.templates.control;

import blogpad.storage.control.*;
import blogpad.templates.entity.Template;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class TemplatesStore {

    @Inject
    @ConfigProperty(name = "storage.dir")
    String rootStorageDir;

    @Inject
    @ConfigProperty(name = "templates.subdir", defaultValue = "templates")
    String templatesSubFolder;

    private Path templatesStorage;

    @PostConstruct
    public void init() {
        Path storageFolder = Path.of(this.rootStorageDir);
        this.templatesStorage = storageFolder.resolve(this.templatesSubFolder);
        this.initializeStorageFolder(this.templatesStorage);

    }

    void initializeStorageFolder(Path path) {

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

    public String save(Template template) {
        template.createdAt = LocalDateTime.now();
        String name = template.name;
        String fileName = FileNames.encode(name);
        try {
            write(Path.of(fileName), template.content);
        } catch (IOException ex) {
            throw new StorageException("Cannot store post " + template + " reason: " + ex.getMessage());
        }
        return fileName;
    }


    void write(Path fileName, String content) throws IOException {
        Path path = this.get(fileName);
        Files.writeString(path, content);
    }

    Path get(Path path) {
        return this.templatesStorage.resolve(path);
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

}
