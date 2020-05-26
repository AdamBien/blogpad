
package blogpad.reactor.boundary;

import blogpad.reactor.control.Content;
import blogpad.reactor.control.Templates;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

/**
 *
 * @author airhacks.com
 */
@ApplicationScoped
public class Reactor {

    private Source handlebars;
    private Source spg;

    @Inject
    Content content;

    @Inject
    Templates templates;

    @PostConstruct
    public void init() {
        try {
            this.handlebars = Source.newBuilder("js", loadScriptFromJar("handlebars-v4.7.6.js"), "Handlebars").build();
            this.spg = Source.newBuilder("js", loadScriptFromJar("spg.js"), "spg").build();
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot load scripts: " + ex.getMessage());
        }
    }

    public String react(String title) {
        String content = this.content.fetchPost(title);
        String template = this.templates.fetchTemplate();
        try {
            return this.react(template, content);
        } catch (IOException ex) {
            throw new IllegalStateException("Cannot create content " + ex);
        }
    }

    public String react(String templateContent, String parameterContentAsJSON) throws IOException {
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
