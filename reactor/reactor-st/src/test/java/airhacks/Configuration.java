package airhacks;

import java.net.URI;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.eclipse.microprofile.config.spi.Converter;

public interface Configuration {
    static String getStringValue(String key) {
        return config().getValue(key, String.class);
    }

    static URI getURIValue(String key) {
        return config().getValue(key, URI.class);
    }

    static Config config() {
        var builder = ConfigProviderResolver.instance().getBuilder();
        return builder.withConverter(URI.class, 1, string -> URI.create(string)).
                addDefaultSources().
                addDiscoveredConverters().
                addDiscoveredSources().
        build();
    }

}