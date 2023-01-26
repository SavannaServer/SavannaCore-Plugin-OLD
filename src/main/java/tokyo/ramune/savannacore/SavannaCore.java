package tokyo.ramune.savannacore;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import tokyo.ramune.savannacore.config.CoreConfig;
import tokyo.ramune.savannacore.database.DatabaseHandler;
import tokyo.ramune.savannacore.debug.DebugHandler;
import tokyo.ramune.savannacore.physics.PhysicsHandler;
import tokyo.ramune.savannacore.server.GameServer;
import tokyo.ramune.savannacore.sidebar.SideBarHandler;
import tokyo.ramune.savannacore.world.WorldHandler;

public final class SavannaCore extends JavaPlugin {
    private static SavannaCore instance;
    private CoreConfig config;
    private DatabaseHandler database;
    private WorldHandler worldHandler;
    private SideBarHandler sideBarHandler;
    private PhysicsHandler physics;
    private DebugHandler debugHandler;
    private GameServer gameServer;

    public static SavannaCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Permission.register();

        config = new CoreConfig(this);
        database = new DatabaseHandler();
        worldHandler = new WorldHandler();
        worldHandler.loadDefaultWorld();
        sideBarHandler = new SideBarHandler();
        database.connect(
                config.value(CoreConfig.Key.DATABASE_HOST, String.class, "localhost"),
                config.value(CoreConfig.Key.DATABASE_PORT, Integer.class, 27017)
        );
        physics = new PhysicsHandler();
        debugHandler = new DebugHandler();

        if (config.value(CoreConfig.Key.DEBUG_MODE, Boolean.class, false)) debugHandler.enable();
        if (!debugHandler.isEnabled()) gameServer = new GameServer();

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

    public enum Permission {
        DEBUG_COMMAND(PermissionDefault.OP);

        private final PermissionDefault def;

        Permission(PermissionDefault def) {
            this.def = def;
        }

        private static void register() {
            for (Permission permission : values()) {
                Bukkit.getPluginManager().addPermission(permission.toPermission());
            }
        }

        public org.bukkit.permissions.Permission toPermission() {
            return new org.bukkit.permissions.Permission(name().toLowerCase(), def);
        }
    }
}
