package tokyo.ramune.savannacore.general;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.GameMode;
import tokyo.ramune.savannacore.util.EventUtil;

import java.util.HashSet;
import java.util.Set;

public final class Game {
    private final Set<Player> joinedPlayers = new HashSet<>();
    private GameMode currentGameMode = null;

    public Game() {
        EventUtil.register(
                SavannaCore.getPlugin(SavannaCore.class),
                new PlayerJoinQuitListener()
        );
    }

    public Set<Player> getJoinedPlayers() {
        return joinedPlayers;
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public void setCurrentGameMode(GameMode curretGameMode) {
        this.currentGameMode = curretGameMode;
    }

    private final static class PlayerJoinQuitListener implements Listener {
        
    }
}
