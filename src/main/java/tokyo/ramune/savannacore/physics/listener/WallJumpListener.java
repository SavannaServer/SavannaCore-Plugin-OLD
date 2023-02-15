package tokyo.ramune.savannacore.physics.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.physics.PhysicsHandler;

import javax.annotation.Nonnull;

public final class WallJumpListener extends PhysicsHandlerListener {
    public WallJumpListener(@Nonnull PhysicsHandler physicsHandler) {
        super(physicsHandler);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        final Player player = event.getPlayer();
        if (!getPhysicsHandler().isAttached(player)) return;

        if (!event.isSneaking()) return;
        if (!event.getPlayer().getLocation().getBlock().isEmpty()) return;

        boolean wallEast = false,
                wallSouth = false,
                wallWest = false,
                wallNorth = false;

        // Check wall exists
        final Location location = player.getLocation();
        final double distance = 0.4;
        final Block eastBlock = location.clone().add(distance, 1, 0).getBlock(),
                southBlock = location.clone().add(0, 1, distance).getBlock(),
                westBlock = location.clone().add(-distance, 1, 0).getBlock(),
                northBlock = location.clone().add(0, 1, -distance).getBlock();

        if (!eastBlock.isEmpty() && !eastBlock.getType().isTransparent() && eastBlock.getType().isOccluding())
            wallEast = true;
        if (!southBlock.isEmpty() && !southBlock.getType().isTransparent() && southBlock.getType().isOccluding())
            wallSouth = true;
        if (!westBlock.isEmpty() && !westBlock.getType().isTransparent() && westBlock.getType().isOccluding())
            wallWest = true;
        if (!northBlock.isEmpty() && !northBlock.getType().isTransparent() && northBlock.getType().isOccluding())
            wallNorth = true;

        apply(player, wallEast, wallSouth, wallWest, wallNorth);
    }

    private void apply(@Nonnull Player player, boolean existWallEast, boolean existWallSouth, boolean existWallWest, boolean existWallNorth) {
        final double multiply = 3;
        final double xzVelocity = 0.4, yVelocity = 0.6;
        final Vector velocity = player.getVelocity().multiply(multiply).setY(player.getVelocity().getY());
        if (existWallEast) {
            final Vector addVector = new Vector(-xzVelocity, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), 0);
            player.setVelocity(velocity.add(addVector));
            return;
        }
        if (existWallSouth) {
            final Vector addVector = new Vector(0, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), -xzVelocity);
            player.setVelocity(velocity.add(addVector));
            return;
        }
        if (existWallWest) {
            final Vector addVector = new Vector(xzVelocity, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), 0);
            player.setVelocity(velocity.add(addVector));
            return;
        }
        if (existWallNorth) {
            final Vector addVector = new Vector(0, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), xzVelocity);
            player.setVelocity(velocity.add(addVector));
        }
    }
}
