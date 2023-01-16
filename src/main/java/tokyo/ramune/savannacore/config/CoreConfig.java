package tokyo.ramune.savannacore.config;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public final class CoreConfig extends ConfigFile {
    public CoreConfig(Plugin plugin) {
        super(plugin, "core_config.yml");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public <T> T value(@Nonnull Key key, @Nonnull Class<T> typeClass) {
        return getConfig().getObject(key.name(), typeClass);
    }

    public <T> T value(@Nonnull Key key, @Nonnull Class<T> typeClass, T def) {
        return getConfig().getObject(key.name(), typeClass, def);
    }

    public void value(@Nonnull Key key, @Nonnull Object value) {
        getConfig().set(key.name(), value);
    }

    public enum Key {
        MYSQL_HOST,
        MYSQL_PORT,
        MYSQL_USER,
        MYSQL_PASSWORD,
        MYSQL_DATABASE
    }
}
