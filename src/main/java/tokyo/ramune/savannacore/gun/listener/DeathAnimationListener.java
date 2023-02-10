package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathAnimationListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getPlayer();
        event.setCancelled(true);
        final Location location = player.getLocation();
        player.teleport(location);
        player.setGameMode(GameMode.SPECTATOR);
        player.setVelocity(location.getDirection().multiply(-0.6));
    }
}