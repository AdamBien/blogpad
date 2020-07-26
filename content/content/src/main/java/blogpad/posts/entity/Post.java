
package blogpad.posts.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author airhacks.com
 */
public class Post {

    public String uniqueName;
    @NotBlank
    public String title;
    @NotBlank
    public String content;
    public LocalDateTime createdAt;
    public LocalDateTime modifiedAt;
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

    public static Post copy(Post updated, Post existing) {
        System.out.println("blogpad.posts.entity.Post.copy() " + updated + " -> " + existing);
        updated.createdAt = existing.createdAt;
        updated.updateModificationTimestamp();
        System.out.println("blogpad.posts.entity.Post.copy() " + updated + " -> " + existing);
        return updated;
    }

    void updateModificationTimestamp() {
        this.modifiedAt = LocalDateTime.now();
    }

    public Post setCreationDate() {
        this.createdAt = LocalDateTime.now();
        return this;
    }

    public String getFileName() {
        return uniqueName;
    }

    public Post withTitle(String fileName) {
        this.uniqueName = fileName;
        return this;
    }

    @Override
    public String toString() {
        return "Post{" + "title=" + title + ", content=" + content + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", comments=" + comments + '}';
    }

}
