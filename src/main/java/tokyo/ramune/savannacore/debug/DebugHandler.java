package tokyo.ramune.savannacore.debug;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.command.Command;
import tokyo.ramune.savannacore.utility.ChatUtil;
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
                "load-world",
                args -> {
                    if (args.getArgs().length > 1 && args.getArgs()[0].isBlank() && !args.getArgs()[0].startsWith("sa.")) {
                        ChatUtil.sendMessage(args.getSender(), "§cArgs are wrong. example-> /load-world sa.<world-name>", true);
                        return false;
                    }
                    SavannaCore.getInstance().getWorldHandler().load(args.getArgs()[0]);
                    return true;
                },
                args -> {
                    return SavannaCore.getInstance().getWorldHandler().getWorldNames();
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
                args -> {
                    return SavannaCore.getInstance().getWorldHandler().getWorldNames();
                },
                SavannaCore.Permission.DEBUG_COMMAND.toPermission()
        ).register();
        new Command(
                "add-spawn",
                args -> {
                    if (!(args.getSender() instanceof Player player)) {
                        CommandUtil.mismatchSender(args.getSender());
                        return false;
                    }
                    if (!player.getWorld().getName().startsWith("sa.")) {
                        ChatUtil.sendMessage(player, "§cYou need to run this command in savanna world.", true);
                        return false;
                    }
                    SavannaCore.getInstance().getWorldHandler().load(player.getWorld().getName()).addSpawnLocation(player.getLocation());
                    return true;
                },
                SavannaCore.Permission.DEBUG_COMMAND.toPermission()
        ).register();
        new Command(
                "reload-world-config",
                args -> {
                    if (!(args.getSender() instanceof Player player)) {
                        CommandUtil.mismatchSender(args.getSender());
                        return false;
                    }
                    if (!player.getWorld().getName().startsWith("sa.")) {
                        ChatUtil.sendMessage(player, "§cYou need to run this command in savanna world.", true);
                        return false;
                    }
                    SavannaCore.getInstance().getWorldHandler().load(player.getWorld().getName()).reloadConfig();

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