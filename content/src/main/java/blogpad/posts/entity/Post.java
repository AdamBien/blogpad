
package blogpad.posts.entity;

import java.time.LocalDateTime;

/**
 *
 * @author airhacks.com
 */
public class Post {

    public String title;
    public String content;
    public LocalDateTime createdAt;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", content=" + content + ", createdAt=" + createdAt + '}';
    }

}
