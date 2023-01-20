package tokyo.ramune.savannacore.server;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.FreeForAll;
import tokyo.ramune.savannacore.gamemode.GameModeHandler;
import tokyo.ramune.savannacore.utility.EventUtil;

import java.util.HashSet;
import java.util.Set;

public final class GameServer {
    private final Set<Player> joinedPlayers = new HashSet<>();
    private final GameModeHandler gameModeHandler = new GameModeHandler(Set.of(new FreeForAll()));

    public GameServer() {
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new PlayerJoinQuitListener()
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
}
