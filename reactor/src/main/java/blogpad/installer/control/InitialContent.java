
package blogpad.installer.control;

/**
 *
 * @author airhacks.com
 */
public class InitialContent {

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
}
