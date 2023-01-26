package tokyo.ramune.savannacore.server;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.GameModeHandler;
import tokyo.ramune.savannacore.utility.EventUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class GameServer {
    private final Set<Player> joinedPlayers = new HashSet<>();
    private final GameModeHandler gameModeHandler = new GameModeHandler(new ArrayList<>());

    public GameServer() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new PlayerJoinQuitListener(),
                new DisableCraftItemListener()
        );
    }

    public Set<Player> getJoinedPlayers() {
        return joinedPlayers;
    }

    public GameModeHandler getGameModeHandler() {
        return gameModeHandler;
    }

    private final static class PlayerJoinQuitListener implements Listener {

    }

    private final static class DisableCraftItemListener implements Listener {
        @EventHandler
        public void onCraftItem(CraftItemEvent event) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
        }
    }
}
