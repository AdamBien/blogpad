package blogpad.reactor.posts.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.json.Json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ReactorTest {
    Reactor cut;
    
    @BeforeEach
    public void init() {
        this.cut = new Reactor();
        this.cut.initialize();
    }
    
    @Test
    public void hello() {
        var expected = "hey, duke";
        var serialized = Json.createObjectBuilder().add("message", "duke").build().toString();
        var result = this.cut.react("hey, {{message}}", serialized);
        System.out.println("result: " + result);
        assertEquals(expected,result);
    }
}