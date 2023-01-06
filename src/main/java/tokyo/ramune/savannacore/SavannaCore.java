package tokyo.ramune.savannacore;

import org.bukkit.plugin.java.JavaPlugin;
import tokyo.ramune.savannacore.config.CoreConfig;
import tokyo.ramune.savannacore.gun.Gun;
import tokyo.ramune.savannacore.physics.Physics;

public final class SavannaCore extends JavaPlugin {
    private CoreConfig config;
    private Physics    physics;
    private Gun        gun;

    @Override
    public void onEnable() {
        config  = new CoreConfig(this);
        physics = new Physics();
        gun = new Gun();

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
