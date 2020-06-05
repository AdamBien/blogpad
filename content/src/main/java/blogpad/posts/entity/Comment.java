
package blogpad.posts.entity;

import java.time.LocalDateTime;

/**
 *
 * @author airhacks.com
 */
public class Comment {

    public LocalDateTime createdAt;
    public String name;
    public String content;
    public boolean approved;

    public Comment(LocalDateTime createdAt, String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Comment() {
    }


    @Override
    public String toString() {
        return "Comment{" + "name=" + name + ", content=" + content + '}';
    }

}
