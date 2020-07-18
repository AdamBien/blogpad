
package blogpad.reactor.posts.boundary;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import blogpad.reactor.posts.control.PostsResourceClient;
import blogpad.reactor.posts.control.Templates;

/**
 *
 * @author airhacks.com
 */
@RequestScoped
public class Reactor {

    private Source handlebars;
    private Source spg;

    @Inject
    @RestClient
    PostsResourceClient posts;

    @Inject
    @RestClient
    Templates templates;

    @Inject
    @ConfigProperty(name = "single.post.template")
    String singlePostTemplate;

    @Inject
    @ConfigProperty(name = "post.list.template")
    String postListTemplate;

    @Timed
    @PostConstruct
    public void initialize() {
        try {
            this.handlebars = Source.newBuilder("js", loadScriptFromJar("handlebars-v4.7.6.js"), "Handlebars").build();
            this.spg = Source.newBuilder("js", loadScriptFromJar("spg.js"), "spg").build();
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot load scripts: " + ex.getMessage());
        }
    }

    @Timed
    public String react(String title) {
        var template = this.getSinglePostTemplate();
        var response = this.posts.getPostByTitle(title);
        String stringifedPost = response.readEntity(String.class);
        return this.react(template, stringifedPost);
    }

    @Timed
    public String react(int max) {
        String template = this.getPostListTemplate();
        String stringifedPost = this.posts.recentPosts(max);
        return this.react(template, stringifedPost);
    }

    String getSinglePostTemplate() {
        JsonObject template = this.templates.getTemplateByName(singlePostTemplate);
        return template.getString("content");
    }

    String getPostListTemplate() {
        JsonObject template = this.templates.getTemplateByName(postListTemplate);
        return template.getString("content");
    }

    public String react(String templateContent, String parameterContentAsJSON) {
        try ( Context context = Context.create("js")) {
            Value bindings = context.getBindings("js");
            context.eval(this.handlebars);
            bindings.putMember("templateContent", templateContent);
            bindings.putMember("parameterContent", parameterContentAsJSON);
            return context.eval(this.spg).asString();
        }
    }

    static Reader loadScriptFromJar(String fileName) {
        System.out.printf("Loading script %s%n", fileName);
        InputStream stream = Thread.currentThread().
                getContextClassLoader().
                getResourceAsStream("js/" + fileName);
        if (stream == null) {
            throw new IllegalStateException("Cannot load: " + fileName);
        }
        return new InputStreamReader(stream);
    }
}
