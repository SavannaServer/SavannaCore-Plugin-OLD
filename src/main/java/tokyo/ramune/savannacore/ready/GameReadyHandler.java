package tokyo.ramune.savannacore.ready;

import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.ready.listener.ApplyPlayerReadyListener;
import tokyo.ramune.savannacore.ready.listener.DisableMoveListener;
import tokyo.ramune.savannacore.utility.EventUtil;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public final class GameReadyHandler {
    private final Set<Player> players = new HashSet<>();

    public GameReadyHandler() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new ApplyPlayerReadyListener(),
                new DisableMoveListener()
        );
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
