
package blogpad.installer.entity;

/**
 *
 * @author airhacks.com
 */
public class Template {

    public String fileName;
    public String content;

    public Template(String name, String content) {
        this.fileName = name;
        this.content = content;
    }

    public Template() {
    }

    @Override
    public String toString() {
        return "Template{" + "fileName=" + fileName + ", content=" + content + '}';
    }


}
