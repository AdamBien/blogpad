
package blogpad.storage.control;

import javax.ws.rs.InternalServerErrorException;

/**
 *
 * @author airhacks.com
 */
public class StorageException extends InternalServerErrorException {

    public StorageException(String message) {
        super(message);
    }
    public StorageException(String message,Exception e) {
        super(message,e);
    }


}
