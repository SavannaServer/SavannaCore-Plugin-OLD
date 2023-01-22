package tokyo.ramune.savannacore.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.utility.EventUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class SideBarHandler {
    private Set<SideBar> sideBars = new HashSet<>();
    private BukkitTask updateTimer;

    public SideBarHandler() {
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        if (updateTimer == null || updateTimer.isCancelled())
            updateTimer = Bukkit.getScheduler().runTaskTimerAsynchronously(
                    SavannaCore.getInstance(),
                    this::updateAll,
                    20, 5
            );
    }

    @Nullable
    public SideBar getCurrentSideBar(Player player) {
        try {
            return sideBars.stream()
                    .filter(sidebar -> sidebar.getPlayer().equals(player))
                    .findFirst()
                    .orElse(null);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Set<SideBar> getSideBars() {
        return sideBars;
    }

    @Nullable
    public SideBar getSideBar(@Nonnull Player player) {
        for (SideBar sideBar : sideBars) {
            if (sideBar.getPlayer().equals(player)) return sideBar;
        }
        return null;
    }

    public void setSideBar(SideBar instance) {
        remove(instance.getPlayer());

        sideBars.add(instance);
    }

    public void updateVisible(@Nonnull Player player) {
        SideBar sideBar = getCurrentSideBar(player);

        if (sideBar == null) return;
    }

    private void updateAll() {
        sideBars.forEach(SideBar::update);
    }

    public void remove(Player player) {
        for (SideBar sideBar : sideBars) {
            if (sideBar.getPlayer().equals(player)) {
                sideBar.remove();
                sideBars.remove(sideBar);
                return;
            }
        }
    }

    public void removeAll() {
        sideBars = new HashSet<>();
    }
}