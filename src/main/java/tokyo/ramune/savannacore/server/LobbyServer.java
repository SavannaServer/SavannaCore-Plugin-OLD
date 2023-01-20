package tokyo.ramune.savannacore.server;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.utility.ChatUtil;
import tokyo.ramune.savannacore.utility.EventUtil;

public final class LobbyServer {
    private Location spawnLocation = null;
    private boolean forceSpawnLocation = false;

    public LobbyServer() {
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new ForceSpawnLocationListener(),
                new JoinQuitMessageListener()
        );
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public boolean isForceSpawnLocation() {
        return forceSpawnLocation;
    }

    public void setForceSpawnLocation(boolean forceSpawnLocation) {
        this.forceSpawnLocation = forceSpawnLocation;
    }

    private final class ForceSpawnLocationListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            if (!forceSpawnLocation) return;
            if (spawnLocation == null) return;
            final Player player = event.getPlayer();

            try {
                player.teleport(spawnLocation);
            } catch (Exception e) {
                ChatUtil.sendMessage(player, "§Internal error: \n" + e, true);
            }
        }
    }

    private final static class JoinQuitMessageListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player player = event.getPlayer();

            event.joinMessage(Component.text("§f[§a+§f] §7" + player.getName() + " joined"));
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            final Player player = event.getPlayer();

            event.quitMessage(Component.text("§f[§c-§f] §7" + player.getName() + " quit"));
        }
    }
}
