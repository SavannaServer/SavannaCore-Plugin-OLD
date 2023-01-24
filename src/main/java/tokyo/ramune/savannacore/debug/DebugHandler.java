package tokyo.ramune.savannacore.debug;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public final class DebugHandler {
    private boolean enabled = false;
    public DebugHandler() {
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