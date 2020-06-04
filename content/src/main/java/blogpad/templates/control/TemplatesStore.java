
package blogpad.templates.control;

import blogpad.storage.control.*;
import static blogpad.storage.control.FileOperations.initializeStorageFolder;
import blogpad.templates.entity.Template;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
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
    String templatesSubdirectory;

    Path templatesDirectory;

    @PostConstruct
    public void init() {
        Path storageFolder = Path.of(this.rootStorageDir);
        this.templatesDirectory = storageFolder.resolve(this.templatesSubdirectory);
        initializeStorageFolder(this.templatesDirectory);

    }

    public String save(Template template) {
        String fileName = template.fileName;
        try {
            write(Path.of(fileName), template.content);
        } catch (IOException ex) {
            throw new StorageException("Cannot store post " + template + " reason: " + ex.getMessage());
        }
        return fileName;
    }

    public List<Template> allTemplates() {
        try {
            return Files.list(this.templatesDirectory).
                    map(this::deserialize).
                    collect(Collectors.toList());
        } catch (IOException ex) {
            throw new StorageException("Cannot list contents of: " + this.templatesDirectory + " reason: " + ex.getMessage());
        }
    }

    Template deserialize(Path fileName) {
        String content = FileOperations.read(fileName);
        return new Template(fileName.toString(), content);
    }


    void write(Path fileName, String content) throws IOException {
        Path path = this.get(fileName);
        Files.writeString(path, content);
    }

    Path get(Path path) {
        return this.templatesDirectory.resolve(path);
    }

}
