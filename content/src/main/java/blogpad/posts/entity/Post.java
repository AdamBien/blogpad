
package blogpad.posts.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author airhacks.com
 */
public class Post {

    public String title;
    public String content;
    public LocalDateTime createdAt;
    public List<Comment> comments;

    public Post(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public Post() {
        this.comments = new ArrayList<>();
    }

    public Post addComment(Comment comment) {
        this.comments.add(comment);
        return this;
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", content=" + content + ", createdAt=" + createdAt + '}';
    }

}
