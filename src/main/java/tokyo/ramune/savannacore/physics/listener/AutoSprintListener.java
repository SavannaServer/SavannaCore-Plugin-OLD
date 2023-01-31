package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;


public final class AutoSprintListener extends PhysicsListener {
    public AutoSprintListener(@Nonnull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!getPhysicsHandler().isAttached(player)) return;

        player.setSprinting(true);
    }
}
