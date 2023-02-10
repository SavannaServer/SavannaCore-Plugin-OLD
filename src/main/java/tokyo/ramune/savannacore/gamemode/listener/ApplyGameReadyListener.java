package tokyo.ramune.savannacore.gamemode.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import tokyo.ramune.savannacore.gamemode.GameReadyHandler;
import tokyo.ramune.savannacore.gamemode.event.PlayerReadyEvent;

import javax.annotation.Nonnull;

public final class ApplyGameReadyListener extends GameReadyListener {
    public ApplyGameReadyListener(@Nonnull GameReadyHandler gameReadyHandler) {
        super(gameReadyHandler);
    }

    @EventHandler
    public void onGameReady(PlayerReadyEvent event) {
        final Player player = event.getPlayer();
        getGameReadyHandler().addPlayer(player);
    }
}
