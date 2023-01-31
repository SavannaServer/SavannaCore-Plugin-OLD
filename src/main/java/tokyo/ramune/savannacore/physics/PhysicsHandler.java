package tokyo.ramune.savannacore.physics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.physics.listener.*;
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
                new PhysicsAutoApplyListener(this),
                new AutoSprintListener(this),
                new SlidingListener(this),
                new WallJumpListener(this),
                new NoHungerListener(this),
                new NoFallDamageListener(this)
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
