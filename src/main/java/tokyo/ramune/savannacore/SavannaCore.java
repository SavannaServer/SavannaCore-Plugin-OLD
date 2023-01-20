package tokyo.ramune.savannacore;

import org.bukkit.plugin.java.JavaPlugin;
import tokyo.ramune.savannacore.config.CoreConfig;
import tokyo.ramune.savannacore.server.GameServer;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

public final class SavannaCore extends JavaPlugin {
    private CoreConfig config;
    private PhysicsHandler physics;
    private GameServer gameServer;

    @Override
    public void onEnable() {
        config = new CoreConfig(this);
        physics = new PhysicsHandler();
        gameServer = new GameServer();

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
    }

    public CoreConfig getCoreConfig() {
        return config;
    }

    public PhysicsHandler getPhysics() {
        return physics;
    }

    public GameServer getGameServer() {
        return gameServer;
    }
}
