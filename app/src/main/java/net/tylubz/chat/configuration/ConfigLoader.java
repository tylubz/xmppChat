package net.tylubz.chat.configuration;

import android.content.res.AssetManager;

import net.tylubz.chat.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads all necessary properties for application
 *
 * @author Sergei Lebedev
 */
public final class ConfigLoader {

    private final Properties properties = new Properties();
    private static final String FILE_NAME = "config.properties";

    private ConfigLoader() {}

    private static class ConfigHolder {
        private static final ConfigLoader INSTANCE = new ConfigLoader();
        static {
            try {
                InputStream inputStream = App.getContext().getAssets().open(FILE_NAME);
                INSTANCE.properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConfigLoader getInstance() throws IOException {
        return ConfigHolder.INSTANCE;
    }

    public Properties getProperties() {
        return ConfigHolder.INSTANCE.properties;
    }
}
