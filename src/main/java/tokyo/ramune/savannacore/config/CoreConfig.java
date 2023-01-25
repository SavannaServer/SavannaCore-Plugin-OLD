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
        return getConfig().getObject("config." + key.name(), typeClass);
    }

    public <T> T value(@Nonnull Key key, @Nonnull Class<T> typeClass, T def) {
        return getConfig().getObject("config." + key.name(), typeClass, def);
    }

    public void value(@Nonnull Key key, @Nonnull Object value) {
        getConfig().set("config." + key.name(), value);
    }

    public enum Key {
        DATABASE_HOST,
        DATABASE_PORT,

        PLAYER_LIMIT,

        DEBUG_MODE
    }
}
