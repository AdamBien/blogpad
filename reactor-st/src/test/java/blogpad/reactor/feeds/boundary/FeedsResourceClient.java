package blogpad.reactor.feeds.boundary;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("feeds")
public interface FeedsResourceClient {

    @GET
    @Path("rss")
    @Produces("application/rss+xml")
    public Response rssFeed(@QueryParam("max") @DefaultValue("-1") int max);

    @GET
    @Path("atom")
    @Produces("application/atom+xml")
    public Response atomFeed(@QueryParam("max") @DefaultValue("-1") int max);
}