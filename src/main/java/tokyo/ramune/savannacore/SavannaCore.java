package tokyo.ramune.savannacore;

import org.bukkit.plugin.java.JavaPlugin;
import tokyo.ramune.savannacore.asset.MessageAsset;
import tokyo.ramune.savannacore.config.CoreConfig;
import tokyo.ramune.savannacore.database.DatabaseHandler;
import tokyo.ramune.savannacore.debug.DebugHandler;
import tokyo.ramune.savannacore.gun.Bullet;
import tokyo.ramune.savannacore.gun.Gun;
import tokyo.ramune.savannacore.gun.GunHandler;
import tokyo.ramune.savannacore.inventory.InventoryHandler;
import tokyo.ramune.savannacore.item.ItemHandler;
import tokyo.ramune.savannacore.language.LanguageHandler;
import tokyo.ramune.savannacore.permission.SavannaPermission;
import tokyo.ramune.savannacore.physics.PhysicsHandler;
import tokyo.ramune.savannacore.server.GameServer;
import tokyo.ramune.savannacore.sidebar.SideBarHandler;
import tokyo.ramune.savannacore.utility.CommandUtil;
import tokyo.ramune.savannacore.world.WorldHandler;

public final class SavannaCore extends JavaPlugin {
    private static SavannaCore instance;

    private CoreConfig config;
    private LanguageHandler languageHandler;
    private DatabaseHandler database;
    private WorldHandler worldHandler;
    private SideBarHandler sideBarHandler;
    private PhysicsHandler physics;
    private ItemHandler itemHandler;
    private DebugHandler debugHandler;
    private GameServer gameServer;

    public static SavannaCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        SavannaPermission.registerAll();

        config = new CoreConfig(this);
        languageHandler = new LanguageHandler();
        database = new DatabaseHandler();
        database.connect(
                config.value(CoreConfig.Key.DATABASE_HOST, String.class, "localhost"),
                config.value(CoreConfig.Key.DATABASE_PORT, Integer.class, 27017)
        );
        worldHandler = new WorldHandler();
        sideBarHandler = new SideBarHandler();
        physics = new PhysicsHandler();
        Bullet.registerListener();
        itemHandler = new ItemHandler();
        if (config.value(CoreConfig.Key.DEBUG_MODE, Boolean.class, false)) debugHandler = new DebugHandler();

        new Gun();
        new GunHandler();
        new InventoryHandler();
        if (debugHandler == null) gameServer = new GameServer();
        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        try {
            if (database != null) database.getClient().close();
        } catch (Exception ignored) {
        }
        SavannaPermission.unregisterAll();
        itemHandler.unregisterAll();
        CommandUtil.unregisterAll();
        getLogger().info("The plugin has been disabled.");
    }

    public CoreConfig getCoreConfig() {
        return config;
    }

    public LanguageHandler getLanguageHandler() {
        return languageHandler;
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

    public ItemHandler getItemHandler() {
        return itemHandler;
    }

    public GameServer getGameServer() {
        return gameServer;
    }
}