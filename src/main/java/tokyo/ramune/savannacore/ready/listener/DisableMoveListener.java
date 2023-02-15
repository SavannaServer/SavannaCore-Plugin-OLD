package tokyo.ramune.savannacore.ready.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class DisableMoveListener extends GameReadyHandlerListener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (!getGameReadyHandler().getPlayers().contains(player)) return;
        event.setCancelled(true);
    }
}
