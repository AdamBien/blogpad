
package blogpad.tracing.boundary;

import java.lang.System.Logger.Level;

/**
 *
 * @author airhacks.com
 */
public class Tracer {

    public static void info(String message) {
        System.getLogger("tracer").log(Level.INFO, message);
    }

}
