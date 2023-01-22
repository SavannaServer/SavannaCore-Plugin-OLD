package tokyo.ramune.savannacore;

import org.bukkit.plugin.java.JavaPlugin;
import tokyo.ramune.savannacore.config.CoreConfig;
import tokyo.ramune.savannacore.database.DatabaseHandler;
import tokyo.ramune.savannacore.server.GameServer;
import tokyo.ramune.savannacore.physics.PhysicsHandler;
import tokyo.ramune.savannacore.sidebar.SideBarHandler;
import tokyo.ramune.savannacore.world.WorldAsset;
import tokyo.ramune.savannacore.world.WorldHandler;

public final class SavannaCore extends JavaPlugin {
    private static SavannaCore instance;

    public static SavannaCore getInstance() {
        return instance;
    }

    private CoreConfig config;
    private DatabaseHandler database;
    private WorldHandler worldHandler;
    private SideBarHandler sideBarHandler;
    private PhysicsHandler physics;
    private GameServer gameServer;

    @Override
    public void onEnable() {
        instance = this;

        config = new CoreConfig(this);
        database = new DatabaseHandler();
        worldHandler = new WorldHandler();
        sideBarHandler = new SideBarHandler();
        database.connect(
                config.value(CoreConfig.Key.DATABASE_HOST, String.class, "localhost"),
                config.value(CoreConfig.Key.DATABASE_PORT, Integer.class, 27017)
        );
        physics = new PhysicsHandler();
        gameServer = new GameServer();

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        database.getClient().close();
        getLogger().info("The plugin has been disabled.");
    }

    public CoreConfig getCoreConfig() {
        return config;
    }

    public DatabaseHandler getDatabase() {
        return database;
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    public SideBarHandler getSideBarHandler() {
        return sideBarHandler;
    }

    public PhysicsHandler getPhysics() {
        return physics;
    }

    public GameServer getGameServer() {
        return gameServer;
    }
}
