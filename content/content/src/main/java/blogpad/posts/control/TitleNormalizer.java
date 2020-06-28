
package blogpad.posts.control;

import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author airhacks.com
 */
public class TitleNormalizer {

    @Inject
    @ConfigProperty(name = "title.replacement.character", defaultValue = "-")
    String replacementCharacter;

    public String normalize(String title) {
        return title.codePoints().
                map(this::replaceWithSeparator).
                collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append).
                toString();
    }

    int replaceWithSeparator(int character) {
        if (Character.isLetterOrDigit(character)) {
            return character;
        }
        return getSeparator();
    }

    int getSeparator() {
        return replacementCharacter.
                codePoints().
                findFirst().
                orElseThrow();
    }
}
