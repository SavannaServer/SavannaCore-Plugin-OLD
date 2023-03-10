package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;

public final class NoFallDamageListener extends PhysicsHandlerListener {
    public NoFallDamageListener(@Nonnull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        final Player player = (Player) event.getEntity();
        if (!getPhysicsHandler().isAttached(player)) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        event.setCancelled(true);
    }
}
