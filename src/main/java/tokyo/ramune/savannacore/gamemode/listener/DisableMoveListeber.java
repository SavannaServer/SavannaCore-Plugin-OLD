package tokyo.ramune.savannacore.gamemode.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import tokyo.ramune.savannacore.gamemode.GameReadyHandler;

import javax.annotation.Nonnull;

public final class DisableMoveListeber extends GameReadyListener {
    public DisableMoveListeber(@Nonnull GameReadyHandler gameReadyHandler) {
        super(gameReadyHandler);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (!getGameReadyHandler().getPlayers().contains(player)) return;
        event.setCancelled(true);
    }
}
