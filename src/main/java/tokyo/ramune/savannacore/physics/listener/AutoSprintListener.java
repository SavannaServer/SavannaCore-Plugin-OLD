package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;


public final class AutoSprintListener extends PhysicsHandlerListener {
    public AutoSprintListener(@Nonnull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!getPhysicsHandler().isAttached(player)) return;

        player.setSprinting(true);
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        event.getPlayer().getInventory().getItemInMainHand().setType(Material.GLASS);
    }
}
