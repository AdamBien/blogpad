
package blogpad.templates.entity;

import java.nio.file.Path;

/**
 *
 * @author airhacks.com
 */
public class Template {

    public String fileName;
    public String templateName;
    public String content;

    public Template(String name, String content) {
        this.fileName = name;
        this.templateName = extract(name);
        this.content = content;
    }

    public Template() {
    }

    static String extract(String fileName) {
        return Path.of(fileName).getFileName().toString();
    }

    @Override
    public String toString() {
        return "Template{" + "fileName=" + fileName + ", content=" + content + '}';
    }


}
