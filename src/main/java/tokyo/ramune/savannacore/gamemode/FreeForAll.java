package tokyo.ramune.savannacore.gamemode;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.sidebar.SideBarHandler;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;

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
    }

    @Override
    public void onUnload() {
        EventUtil.unregister(new SideBarListener());
        final FreeForAll instance = this;
        for (Player player : getPlayingPlayers()) {
            player.sendTitlePart(TitlePart.TITLE, Component.text("Win!").color(TextColor.color(0xE2972B)));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                instance.unload();
            }
        }.runTaskLater(SavannaCore.getInstance(), 100);
    }

    private void unload() {
        super.onUnload();
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
            super(player, Component.text("Savanna Server"));

            addBlankLine();
            addLine(() -> Component.text(getClass().getSimpleName()));
            addBlankLine();
            addLine(() -> Component.text("Time left:"));
            addLine(() -> Component.text(Util.formatElapsedTime(getCurrentTime())));
        }
    }
}
