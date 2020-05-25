
package blogpad.storage.control;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

/**
 *
 * @author airhacks.com
 */
public interface FileNames {

    static String createFileName(String title) {
        String rawName = prefix() + title;
        return encode(rawName);
    }

    static String encode(String rawName) {
        try {
            return URLEncoder.encode(rawName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Unsupported encoding", ex);
        }
    }

    static String prefix() {
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
                .appendLiteral('_')
                .toFormatter();
        return timeFormatter.format(now);
    }



}
