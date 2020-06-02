
package blogpad.templates.entity;

import java.time.LocalDateTime;

/**
 *
 * @author airhacks.com
 */
public class Template {

    public String name;
    public String content;
    public LocalDateTime createdAt;

    public Template(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Template() {
    }

    @Override
    public String toString() {
        return "Template{" + "name=" + name + ", content=" + content + ", createdAt=" + createdAt + '}';
    }


}
