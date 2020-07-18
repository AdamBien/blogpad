package blogpad.logging.boundary;

import java.lang.System.Logger.Level;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LogProducer {

    @Produces
    public Blogger produce(InjectionPoint ip) {
        var name = ip.getMember().getClass().getName();
        var logger = System.getLogger(name);
        return (message) -> logger.log(Level.INFO, message);
    }
    
}