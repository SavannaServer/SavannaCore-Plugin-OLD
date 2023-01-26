package tokyo.ramune.savannacore.debug;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.command.Command;
import tokyo.ramune.savannacore.utility.CommandUtil;
import tokyo.ramune.savannacore.utility.EventUtil;

public final class DebugHandler {
    private boolean enabled = false;

    public DebugHandler() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new DisablePlayerLogin()
        );
        new Command(
                "get-worlds",
                args -> {
                    for (String name : SavannaCore.getInstance().getWorldHandler().getWorldNames()) {
                        args.getSender().sendMessage(name);
                    }
                    return true;
                },
                SavannaCore.Permission.DEBUG_COMMAND.toPermission()
        ).register();
        new Command(
                "tp-world",
                args -> {
                    if (!(args.getSender() instanceof Player player)) {
                        CommandUtil.mismatchSender(args.getSender());
                        return false;
                    }
                    try {
                        player.teleport(SavannaCore.getInstance().getWorldHandler().load(args.getArgs()[0]).getWorld().getSpawnLocation());
                    } catch (Exception ignored) {
                    }
                    return true;
                },
                SavannaCore.Permission.DEBUG_COMMAND.toPermission()
        ).register();
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private final static class DisablePlayerLogin implements Listener {
        @EventHandler
        public void onPlayerLogin(PlayerLoginEvent event) {
            final Player player = event.getPlayer();

            if (player.isOp()) return;
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.kickMessage(Component.text("§This server is under debug mode. \nOnly ops can join this server."));
        }
    }
}