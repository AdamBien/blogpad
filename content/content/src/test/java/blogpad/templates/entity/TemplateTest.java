package blogpad.templates.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TemplateTest {

    @Test
    public void extractFileName() {
        var expected = "template.html";
        var fqn = "/tmp/templates/" + expected;
        var actual = Template.extract(fqn);
        assertEquals(expected, actual);
    }
    
}