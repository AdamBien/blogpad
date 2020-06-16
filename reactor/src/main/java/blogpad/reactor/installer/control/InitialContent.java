
package blogpad.reactor.installer.control;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author airhacks.com
 */
public class InitialContent {

    @Inject
    @ConfigProperty(name = "feed.title", defaultValue = "blogpad news")
    String feedTitle;

    @Inject
    @ConfigProperty(name = "feed.author", defaultValue = "duke")
    String feedAuthor;

    @Inject
    @ConfigProperty(name = "blog.base.uri", defaultValue = "http://localhost:9080/reactor/resources/posts")
    String blogBaseURI;

    public String getFirstPost() {
        return """
            {
                   "content":
                   "hello, world",
                   "title":
                   "welcome to blogpad"
               }
               """;
    }

    public String getSinglePostTemplate() {
        return """
                  <article>
                    <head>
                        <h1>{{title}}</h1>
                    </head>
                    <section>
                       {{content}}
                    </section>
                    <footer>
                        <small>created: {{createdAt}}, modified: {{modifiedAt}}</small>
                    </footer>
                </article>
                """;
    }

    public String getPostListTemplate() {
        return """
                {{#posts}}
                    <article>
                        <head>
                            <h1>{{title}}</h1>
                        </head>
                        <section>
                            {{content}}
                        </section>
                        <footer>
                            <small>created: {{createdAt}}, modified: {{modifiedAt}}</small>
                        </footer>
                    </article>
                {{/posts}}
                """;
    }

    public String getRssFeedTemplate() {
        return"""
              """;
    }
    
    public String getAtomFeedTemplate() {
        return """
                <?xml version="1.0" encoding="utf-8"?>
                <feed xmlns="http://www.w3.org/2005/Atom">

                <title>%s</title>
                <link href="http://example.org/"/>
                <updated>TBD -> take from recent post</updated>
                <author>
                    <name>%s</name>
                </author>
                <id>urn:uuid:60a76c80-d399-11d9-b93C-0003939e0af6</id>
                  {{#posts}}
                    <entry>
                        <title>{{title}}</title>
                        <link rel="alternate" type="text/html" href="%s/{{fileName}}"/>
                        <id>{{fileName}}</id>
                        <updated>{{modifiedAt}}</updated>
                        <content type="html">
                            {{content}}
                        </content>
                    </entry>
                    </feed>
                  {{/posts}}
                      """.formatted(feedTitle, feedAuthor,baseUri);
    }

}
