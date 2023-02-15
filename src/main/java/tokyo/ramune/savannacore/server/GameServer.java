package tokyo.ramune.savannacore.server;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.GameModeHandler;
import tokyo.ramune.savannacore.ready.GameReadyHandler;
import tokyo.ramune.savannacore.utility.EventUtil;

import java.util.ArrayList;

public final class GameServer {
    private final GameModeHandler gameModeHandler = new GameModeHandler(new ArrayList<>());
    private final GameReadyHandler gameReadyHandler = new GameReadyHandler();

    public GameServer() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new DisableCraftItemListener()
        );
    }

    public GameModeHandler getGameModeHandler() {
        return gameModeHandler;
    }

    public GameReadyHandler getGameReadyHandler() {
        return gameReadyHandler;
    }

    private final static class DisableCraftItemListener implements Listener {
        @EventHandler
        public void onCraftItem(CraftItemEvent event) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
}
