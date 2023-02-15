package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import tokyo.ramune.savannacore.SavannaCore;

import java.util.Set;

public final class DeathAnimationListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getPlayer();
        final Set<Player> playingPlayers = SavannaCore.getInstance().getGameServer().getGameModeHandler().getCurrentGameMode().getPlayingPlayers();

        if (!playingPlayers.contains(player)) return;
        event.setCancelled(true);
        final Location location = event.getEntity().getLocation();
        player.teleport(location);
        player.setGameMode(GameMode.SPECTATOR);
        player.setVelocity(location.getDirection().multiply(-0.6));
    }
}