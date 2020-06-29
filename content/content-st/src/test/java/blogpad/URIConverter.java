package blogpad;

import java.net.URI;

import org.eclipse.microprofile.config.spi.Converter;

public class URIConverter implements Converter<URI> {

	@Override
    public URI convert(String value) {
        return URI.create(value);
	}
    
}