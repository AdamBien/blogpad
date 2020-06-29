package blogpad.reactor.posts.boundary;

import static org.mockito.Mockito.mock;

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
        var serialized = Json.createObjectBuilder().add("message", "duke").build().toString();
        var result = this.cut.react("hey, {{message}}", serialized);
        System.out.println("result " + result);
    }
}