package blogpad;

import java.net.URI;

import org.eclipse.microprofile.config.ConfigProvider;

public interface Configuration {
    static String getStringValue(String key) {
        var config = ConfigProvider.getConfig();
        return config.getValue(key, String.class);
    }

    static URI getURIValue(String key) {
        var config = ConfigProvider.getConfig();
        return config.getValue(key, URI.class);
    }

}