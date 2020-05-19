
package blogpad.storage.control;

import blogpad.posts.entity.Post;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author airhacks.com
 */
public class BlogStore {

    @Inject
    @ConfigProperty(name = "BLOG_STORE_FOLDER")
    String folder;

    private Jsonb jsonb;

    Path storageFolder;

    @PostConstruct
    public void init() {
        this.storageFolder = Path.of(this.folder);
        JsonbConfig config = new JsonbConfig().
                withFormatting(true);
        this.jsonb = JsonbBuilder.newBuilder().
                withConfig(config).
                build();
    }


    String serialize(Post post) {
        return this.jsonb.toJson(post);
    }

    void write(String fileName, String content) throws IOException {
        Path path = this.get(fileName);
        Files.writeString(path, content);
    }

    Path get(String path) {
        return this.storageFolder.resolve(path);
    }

    String read(String fileName) throws IOException {
        Path path = this.get(fileName);
        return Files.readString(path);
    }

    static String fileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4)
                .appendLiteral('_')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('_')
                .appendValue(DAY_OF_MONTH, 2)
                .appendLiteral("__")
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral('_')
                .appendValue(MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral('_')
                .appendValue(SECOND_OF_MINUTE, 2)
                .appendLiteral('_')
                .appendValue(MILLI_OF_SECOND, 4)
                .toFormatter();
        return timeFormatter.format(now);
    }

}
