package tokyo.ramune.savannacore.physics;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.utility.CommandUtil;
import tokyo.ramune.savannacore.utility.EventUtil;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class PhysicsHandler {
    private static PhysicsHandler instance;

    private final Set<Player> attachedPlayers;

    public PhysicsHandler() {
        instance = this;

        attachedPlayers = new HashSet<>();
        EventUtil.register(
                SavannaCore.getInstance(),
                new PhysicsAutoApplyListener(),
                new AutoSprintListener(),
                new SlidingListener(),
                new WallJumpListener(),
                new NoHungerListener(),
                new NoFallDamageListener()
        );
        CommandUtil.register(
                SavannaCore.getInstance().getName(),
                new EnableCommand(),
                new DisableCommand()
        );

        applyJoinedPlayers();
    }

    public void apply(@Nonnull Player player, boolean attach) {
        if (attach) {
            attachedPlayers.add(player);
        } else {
            attachedPlayers.remove(player);
        }
    }

    public boolean isAttached(@Nonnull Player player) {
        return attachedPlayers.contains(player);
    }

    private void applyJoinedPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            apply(player, true);
        }
    }

    // Event listeners

    // Auto apply physics joined players.
    private static final class PhysicsAutoApplyListener implements Listener {
        @EventHandler(priority = EventPriority.LOWEST)
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player player = event.getPlayer();

            Bukkit.getServer().unloadWorld(player.getWorld(), false);

            instance.apply(player, true);
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            final Player player = event.getPlayer();

            instance.apply(player, false);
        }
    }

    // Sliding system
    private static final class SlidingListener implements Listener {
        private final Set<Player> allowedSlidingPlayers = new HashSet<>();

        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();

            if (!instance.isAttached(player)) return;

            if (!allowedSlidingPlayers.contains(player)) return;
            if (!((LivingEntity) player).isOnGround()) return;
            allowedSlidingPlayers.remove(player);
            if (!player.isSneaking()) return;

            final Vector currentVelocity = player.getVelocity();
            final Vector velocity = player.getLocation().getDirection();
            velocity.multiply(Math.min(Math.abs(currentVelocity.getX()) + Math.abs(currentVelocity.getZ()), 0.7) * 3.6);
            velocity.setY(currentVelocity.getY());

            player.setVelocity(velocity);
            Sound sound = player.getLocation().add(0, -1, 0).getBlock().getBlockSoundGroup().getStepSound();
            player.playSound(player.getLocation(), sound, 1, 1);
        }

        @EventHandler
        public void onPlayerJump(PlayerJumpEvent event) {
            final Player player = event.getPlayer();
            if (!instance.isAttached(player)) return;

            allowedSlidingPlayers.add(player);
        }
    }

    // Wall jump system
    private static final class WallJumpListener implements Listener {
        @EventHandler
        public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
            final Player player = event.getPlayer();
            if (!instance.isAttached(player)) return;

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

            // Apply velocity
            final double multiply = 3;
            final double xzVelocity = 0.4, yVelocity = 0.6;
            final Vector velocity = player.getVelocity().multiply(multiply).setY(player.getVelocity().getY());
            if (wallEast) {
                final Vector addVector = new Vector(-xzVelocity, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), 0);
                player.setVelocity(velocity.add(addVector));
                return;
            }
            if (wallSouth) {
                final Vector addVector = new Vector(0, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), -xzVelocity);
                player.setVelocity(velocity.add(addVector));
                return;
            }
            if (wallWest) {
                final Vector addVector = new Vector(xzVelocity, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), 0);
                player.setVelocity(velocity.add(addVector));
                return;
            }
            if (wallNorth) {
                final Vector addVector = new Vector(0, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), xzVelocity);
                player.setVelocity(velocity.add(addVector));
            }
        }
    }

    // Force sprint
    private static final class AutoSprintListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();
            if (!instance.isAttached(player)) return;

            player.setSprinting(true);
        }
    }

    // Force no hunger
    private static final class NoHungerListener implements Listener {
        @EventHandler
        public void onFoodLevelChange(FoodLevelChangeEvent event) {
            if (!event.getEntityType().equals(EntityType.PLAYER)) return;
            final Player player = (Player) event.getEntity();
            if (!instance.isAttached(player)) return;
            event.setFoodLevel(20);
        }
    }

    // Force no fall damage
    private static final class NoFallDamageListener implements Listener {
        @EventHandler
        public void onEntityDamage(EntityDamageEvent event) {
            if (!event.getEntityType().equals(EntityType.PLAYER)) return;
            final Player player = (Player) event.getEntity();
            if (!instance.isAttached(player)) return;
            if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
            event.setCancelled(true);
        }
    }

    // Commands

    // Enable physics command
    private static final class EnableCommand extends Command {
        private EnableCommand() {
            super("enable-physics");
        }

        @Override
        public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
            Player targetPlayer = null;
            try {
                targetPlayer = (Player) sender;
            } catch (Exception ignored) {
            }
            try {
                targetPlayer = Bukkit.getPlayer(args[0]);
            } catch (Exception ignored) {
            }

            if (targetPlayer == null) {
                CommandUtil.mismatchSender(sender);
                return false;
            }
            PhysicsHandler.instance.apply(targetPlayer, true);
            CommandUtil.success(sender);
            targetPlayer.sendMessage(ChatColor.GREEN + "Physics enabled!");
            SoundAsset.SUCCESS.play(targetPlayer);
            return true;
        }
    }

    // disable physics command
    private static final class DisableCommand extends Command {
        private DisableCommand() {
            super("disable-physics");
        }

        @Override
        public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
            Player targetPlayer = null;
            try {
                targetPlayer = (Player) sender;
            } catch (Exception ignored) {
            }
            try {
                targetPlayer = Bukkit.getPlayer(args[0]);
            } catch (Exception ignored) {
            }

            if (targetPlayer == null) {
                CommandUtil.mismatchSender(sender);
                return false;
            }
            PhysicsHandler.instance.apply(targetPlayer, false);
            CommandUtil.success(sender);
            targetPlayer.sendMessage(ChatColor.RED + "Physics disabled!");
            SoundAsset.SUCCESS.play(targetPlayer);
            return true;
        }
    }
}
