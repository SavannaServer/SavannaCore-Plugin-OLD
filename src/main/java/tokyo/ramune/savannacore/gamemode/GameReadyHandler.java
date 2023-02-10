package tokyo.ramune.savannacore.gamemode;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class GameReadyHandler {
    private final Set<Player> players = new HashSet<>();

    public GameReadyHandler() {
    }

    public void addPlayer(@Nonnull Player player) {
        players.add(player);
    }

    public void removePlayer(@Nonnull Player player) {
        players.remove(player);
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
