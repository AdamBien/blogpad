/*
 */
package blogpad.posts.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class PostTest {

    public PostTest() {
    }

    @Test
    public void copy() {
        Post old = new Post("t", "o");
        old.setCreationDate();
        Post updated = new Post("t", "new");

        Post afterUpdate = Post.copy(updated, old);
        assertEquals(afterUpdate.createdAt, old.createdAt);
        assertNotNull(updated.modifiedAt);
        assertNull(old.modifiedAt);

    }

}
