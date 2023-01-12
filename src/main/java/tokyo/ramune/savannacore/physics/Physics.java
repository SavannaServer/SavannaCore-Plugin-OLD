package tokyo.ramune.savannacore.physics;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.util.CommandUtil;
import tokyo.ramune.savannacore.util.EventUtil;
import tokyo.ramune.savannacore.util.SoundUtil;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

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

    // Event listeners

    // Auto apply physics joined players.
    private static class PhysicsAutoApplyListener implements Listener {
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

    // Force sprint
    static class AutoSprintListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();
            if (!instance.isAttached(player)) return;

            player.setSprinting(true);
        }
    }

    // Sliding system
    private static class SlidingListener implements Listener {
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
    private static class WallJumpListener implements Listener {
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

    // Jump pad system
    // TODO: 2023/01/07 This used a stone pressure pad temporarily to see how it work.
    //  Make original jump pad.
    private static class JumpPadListener implements Listener {
        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {
            final Player player = event.getPlayer();

            if (!instance.isAttached(player)) return;

            if (!((LivingEntity) player).isOnGround()) return;
            if (!player.getLocation().getBlock().getType().equals(Material.SCULK_SENSOR)) return;
            final JumpPad jumpPad = new JumpPad(player.getLocation().getBlock());
            if (jumpPad.getVelocity() <= 0) return;

            player.setVelocity(player.getVelocity().multiply(1.3).setY(jumpPad.getVelocity()));
            SoundUtil.jumpPad(player);
        }

        @EventHandler
        public void onGenericGame(BlockReceiveGameEvent event) {
            if (!event.getBlock().getType().equals(Material.SCULK_SENSOR)) return;
            event.setCancelled(true);
            final Entity entity = event.getEntity();
            if (!(entity instanceof final Player player)) return;
            player.stopSound(Sound.BLOCK_SCULK_SENSOR_CLICKING_STOP);
        }
    }

    // Force no hunger
    private static class NoHungerListener implements Listener {
        @EventHandler
        public void onFoodLevelChange(FoodLevelChangeEvent event) {
            if (!event.getEntityType().equals(EntityType.PLAYER)) return;
            final Player player = (Player) event.getEntity();
            if (!instance.isAttached(player)) return;
            event.setFoodLevel(20);
        }
    }

    // Force no fall damage
    private static class NoFallDamageListener implements Listener {
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
    private static class EnableCommand extends Command {
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

    // disable physics command
    private static class DisableCommand extends Command {
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
