package blogpad.posts.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Posts
 */
public class Posts {

    public List<Post> posts;

    public Posts() {
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public Posts add(Posts posts) {
        this.posts.addAll(posts.posts);
        return this;
    }


}