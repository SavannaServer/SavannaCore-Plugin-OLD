package tokyo.ramune.savannacore.physics;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.util.CommandUtil;
import tokyo.ramune.savannacore.util.EventUtil;
import tokyo.ramune.savannacore.util.SoundUtil;

import javax.annotation.Nonnull;
import java.util.*;

public class Physics {
    private static Physics instance;

    private final Set<Player> attachedPlayers;

    public Physics() {
        instance = this;

        attachedPlayers = new HashSet<>();
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new PhysicsAutoApplyListener(),
                new AutoSprintListener(),
                new SlidingListener(),
                new WallJumpListener(),
                new JumpPadListener(),
                new NoHungerListener(),
                new NoFallDamageListener()
        );
        CommandUtil.register(
                SavannaCore.getPlugin(SavannaCore.class).getName(),
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

    static class PhysicsAutoApplyListener implements Listener {
        private final Physics physics = instance;

        @EventHandler(priority = EventPriority.LOWEST)
        public void onPlayerJoin(PlayerJoinEvent event) {
            final Player player = event.getPlayer();

            physics.apply(player, true);
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            final Player player = event.getPlayer();

            physics.apply(player, false);
        }
    }

    static class AutoSprintListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();

            player.setSprinting(true);
        }
    }

    static class SlidingListener implements Listener {
        private final Physics physics = instance;
        private final Set<Player> allowedSlidingPlayers = new HashSet<>();

        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();

            if (!physics.isAttached(player)) return;
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
            if (!physics.isAttached(player)) return;

            allowedSlidingPlayers.add(player);
        }
    }

    static class WallJumpListener implements Listener {
        private final Physics physics = instance;

        @EventHandler
        public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
            final Player player = event.getPlayer();
            if (!physics.isAttached(player)) return;
            if (!event.isSneaking()) return;
            if (!event.getPlayer().getLocation().getBlock().isEmpty()) return;

            boolean wallEast = false,
                    wallSouth = false,
                    wallWest = false,
                    wallNorth = false;

            // Check wall exists
            final Location location = player.getLocation();
            double distance = 0.4;
            Block eastBlock  = location.clone().add(distance, 1, 0).getBlock(),
                  southBlock = location.clone().add(0, 1, distance).getBlock(),
                  westBlock  = location.clone().add(-distance, 1, 0).getBlock(),
                  northBlock = location.clone().add(0, 1, -distance).getBlock();

            if (!eastBlock.isEmpty() && !eastBlock.getType().isTransparent() && eastBlock.getType().isOccluding()) wallEast = true;
            if (!southBlock.isEmpty() && !southBlock.getType().isTransparent() && southBlock.getType().isOccluding()) wallSouth = true;
            if (!westBlock.isEmpty() && !westBlock.getType().isTransparent() && westBlock.getType().isOccluding()) wallWest = true;
            if (!northBlock.isEmpty() && !northBlock.getType().isTransparent() && northBlock.getType().isOccluding()) wallNorth = true;

            Vector velocity = player.getVelocity();
            double multiply = 3;
            double xzVelocity = 0.4, yVelocity = 0.6;
            if (wallEast) {
                player.setVelocity(velocity.clone()
                        .multiply(multiply)
                        .setY(velocity.getY())
                        .add(new Vector(-xzVelocity, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), 0)));
                return;
            }
            if (wallSouth) {
                player.setVelocity(velocity.clone()
                        .multiply(multiply)
                        .setY(velocity.getY())
                        .add(new Vector(0, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), -xzVelocity)));
                return;
            }
            if (wallWest) {
                player.setVelocity(velocity.clone()
                        .multiply(multiply)
                        .setY(velocity.getY())
                        .add(new Vector(xzVelocity, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), 0)));
                return;
            }
            if (wallNorth) {
                player.setVelocity(velocity.clone()
                        .multiply(multiply)
                        .setY(velocity.getY())
                        .add(new Vector(0, velocity.getY() < 0 ? yVelocity : Math.min(yVelocity, velocity.getY()), xzVelocity)));
                return;
            }
        }
    }

    static class JumpPadListener implements Listener {
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent event) {
            final Player player = event.getPlayer();

            if (!event.getAction().equals(Action.PHYSICAL)) return;
            if (!Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.STONE_PRESSURE_PLATE)) return;
            player.setVelocity(player.getVelocity().setY(1));
            event.setCancelled(true);
        }
    }

    static class NoHungerListener implements Listener {
        private final Physics physics = instance;

        @EventHandler
        public void onFoodLevelChange(FoodLevelChangeEvent event) {
            if (!event.getEntityType().equals(EntityType.PLAYER)) return;
            final Player player = (Player) event.getEntity();
            if (!physics.isAttached(player)) return;
            event.setFoodLevel(20);
        }
    }

    static class NoFallDamageListener implements Listener {
        private final Physics physics = instance;

        @EventHandler
        public void onEntityDamage(EntityDamageEvent event) {
            if (!event.getEntityType().equals(EntityType.PLAYER)) return;
            final Player player = (Player) event.getEntity();
            if (!physics.isAttached(player)) return;
            if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
            event.setCancelled(true);
        }
    }

    static class EnableCommand extends Command {
        protected EnableCommand() {
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
            Physics.instance.apply(targetPlayer, true);
            CommandUtil.success(sender);
            targetPlayer.sendMessage(ChatColor.GREEN + "Physics enabled!");
            SoundUtil.success(targetPlayer);
            return true;
        }
    }

    static class DisableCommand extends Command {
        protected DisableCommand() {
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
            Physics.instance.apply(targetPlayer, false);
            CommandUtil.success(sender);
            targetPlayer.sendMessage(ChatColor.RED + "Physics disabled!");
            SoundUtil.success(targetPlayer);
            return true;
        }
    }
}
