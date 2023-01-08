package tokyo.ramune.savannacore;

import org.bukkit.plugin.java.JavaPlugin;
import tokyo.ramune.savannacore.config.CoreConfig;
import tokyo.ramune.savannacore.physics.Physics;

public final class SavannaCore extends JavaPlugin {
    private CoreConfig config;
    private Physics physics;

    @Override
    public void onEnable() {
        config = new CoreConfig(this);
        physics = new Physics();

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

    public CoreConfig getCoreConfig() {
        return config;
    }

    public Physics getPhysics() {
        return physics;
    }
}
