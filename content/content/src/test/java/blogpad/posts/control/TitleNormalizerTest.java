/*
 */
package blogpad.posts.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author airhacks.com
 */
public class TitleNormalizerTest {

    private TitleNormalizer cut;


    @BeforeEach
    public void init() {
        this.cut = new TitleNormalizer();
        this.cut.replacementCharacter = "-";
    }

    public static TitleNormalizer create() {
        var test = new TitleNormalizerTest();
        test.init();
        return test.cut;
    }

    @Test
    public void validTitle() {
        String expected = "helloworld";
        String actual = this.cut.normalize(expected);
        assertEquals(expected,actual);
    }

    @Test
    public void titleWithSlash() {
        String input = "hello/world";
        String actual = this.cut.normalize(input);

        assertEquals("hello-world",actual);
    }

    @Test
    public void emptyTitle() {
        String input = "";
        String actual = this.cut.normalize(input);
        assertEquals("",actual);
    }

    @Test
    public void capitalizedTitle() {
        String input = "HelloWorld";
        String actual = this.cut.normalize(input);
        assertEquals("helloworld",actual);
        
    }

}
