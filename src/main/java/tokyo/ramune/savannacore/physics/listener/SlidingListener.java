package tokyo.ramune.savannacore.physics.listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class SlidingListener extends PhysicsListener {
    private final Set<Player> allowedSlidingPlayers = new HashSet<>();

    public SlidingListener(@NotNull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();

        if (!getPhysicsHandler().isAttached(player)) return;
        if (!allowedSlidingPlayers.contains(player)) return;
        if (!((LivingEntity) player).isOnGround()) return;
        allowedSlidingPlayers.remove(player);
        if (!player.isSneaking()) return;
        applySliding(player);
    }

    private void applySliding(Player player) {
        final Vector currentVelocity = player.getVelocity();
        final Vector velocity = player.getLocation().getDirection();
        velocity.multiply(Math.min(Math.abs(currentVelocity.getX()) + Math.abs(currentVelocity.getZ()), 0.7) * 3.6);
        velocity.setY(currentVelocity.getY() + 0.1);

        player.setVelocity(velocity);
        Sound sound = player.getLocation().add(0, -1, 0).getBlock().getBlockSoundGroup().getStepSound();
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent event) {
        final Player player = event.getPlayer();
        if (!getPhysicsHandler().isAttached(player)) return;

        allowedSlidingPlayers.add(player);
    }
}
