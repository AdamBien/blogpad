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


    @Test
    public void validTitle() {
        String expected = "helloworld";
        String actual = this.cut.normalize(expected);
        assertEquals(actual, expected);
    }

    @Test
    public void titleWithSlash() {
        String input = "hello/world";
        String actual = this.cut.normalize(input);

        assertEquals(actual, "hello-world");
    }

}
