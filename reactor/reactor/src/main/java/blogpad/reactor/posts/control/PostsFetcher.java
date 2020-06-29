package blogpad.reactor.posts.control;

import javax.inject.Inject;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;

public class PostsFetcher implements PostsResourceClient{

    @Inject
    @RestClient
    PostsResourceClient client;

    @Retry
	@Override
	public String getPostByTitle(String title) {
        return this.client.getPostByTitle(title);
	}

    @Retry
	@Override
    public String recentPosts(int max) {
        return this.client.recentPosts(max);
    }
    
}