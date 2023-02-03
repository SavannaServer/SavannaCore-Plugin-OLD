package tokyo.ramune.savannacore.gamemode;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.sidebar.SideBarHandler;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;
import tokyo.ramune.savannacore.world.SavannaWorld;

public final class FreeForAll extends GameMode {
    public FreeForAll() {
        super("FFA", 240);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        final SideBarHandler sideBarHandler = SavannaCore.getInstance().getSideBarHandler();

        for (Player player : Bukkit.getOnlinePlayers()) {
            final SideBar sideBar = new SideBar(player);
            sideBarHandler.setSideBar(sideBar);
            sideBar.show();
        }

        EventUtil.register(
                SavannaCore.getInstance(),
                new SideBarListener()
        );
        final SavannaWorld world = SavannaCore.getInstance().getGameServer().getGameModeHandler().getCurrentWorld();
        Util.teleport(Util.getRandom(world.getSpawnLocations()));
    }

    @Override
    public void onUnload() {
        super.onUnload();

        EventUtil.unregister(new SideBarListener());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(Component.text(getCurrentTime()));
        }
    }

    private final class SideBarListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player player = event.getPlayer();
            final SideBarHandler sideBarHandler = SavannaCore.getInstance().getSideBarHandler();

            final SideBar sideBar = new SideBar(player);
            sideBarHandler.setSideBar(sideBar);
            sideBar.show();
        }
    }

    private final class SideBar extends tokyo.ramune.savannacore.sidebar.SideBar {
        public SideBar(Player player) {
            super(player, "Savanna");

            addBlankLine();
            addLine(() -> getClass().getSimpleName());
            addBlankLine();
            addLine(() -> "Time left:");
            addLine(() -> Util.formatElapsedTime(getCurrentTime()));
        }
    }
}
