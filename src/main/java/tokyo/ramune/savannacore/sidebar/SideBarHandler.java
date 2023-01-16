package tokyo.ramune.savannacore.sidebar;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.util.EventUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public final class SideBarHandler {
    private final Set<SideBar> sideBars = new HashSet<>();

    public SideBarHandler() {
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new SideBarInitializeListener()
        );
    }

    @Nullable
    public SideBar getSideBar(@Nonnull Player player) {
        return sideBars.stream()
                .filter(sideBar -> sideBar.getPlayer().equals(player))
                .findFirst()
                .orElse(null);
    }

    public SideBarHandler remove(@Nonnull Player player) {
        final SideBar existsSideBar = getSideBar(player);
        if (existsSideBar != null) {
            existsSideBar.remove();
            sideBars.remove(existsSideBar);
        }

        return this;
    }

    private final class SideBarInitializeListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player player = event.getPlayer();

            remove(player);
            sideBars.add(new SideBar(player, ""));
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            final Player player = event.getPlayer();

            remove(player);
        }
    }
}