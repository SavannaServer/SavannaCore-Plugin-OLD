package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.jetbrains.annotations.NotNull;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;

public final class NoHungerListener extends PhysicsListener {
    public NoHungerListener(@NotNull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        final Player player = (Player) event.getEntity();
        if (!getPhysicsHandler().isAttached(player)) return;
        event.setFoodLevel(20);
    }
}
