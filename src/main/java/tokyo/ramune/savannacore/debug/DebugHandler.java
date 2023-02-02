package tokyo.ramune.savannacore.debug;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.debug.command.*;
import tokyo.ramune.savannacore.utility.CommandUtil;
import tokyo.ramune.savannacore.utility.EventUtil;

public final class DebugHandler {
    public DebugHandler() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new DisablePlayerLogin()
        );
        CommandUtil.register(
                new AddSpawnCommand(),
                new GetWorldsCommand(),
                new LoadWorldCommand(),
                new ReloadWorldConfigCommand(),
                new TpWorldCommand()
        );
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