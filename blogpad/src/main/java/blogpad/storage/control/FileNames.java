
package blogpad.storage.control;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author airhacks.com
 */
public class FileNames {

    static String fileName(String rawName) {
        try {
            return URLEncoder.encode(rawName, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException("Unsupported encoding", ex);
        }
    }


}
