package tokyo.ramune.savannacore.gamemode.listener;

import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.gamemode.GameReadyHandler;

import javax.annotation.Nonnull;

public class GameReadyListener implements Listener {
    private final GameReadyHandler gameReadyHandler;

    public GameReadyListener(@Nonnull GameReadyHandler gameReadyHandler) {
        this.gameReadyHandler = gameReadyHandler;
    }

    public GameReadyHandler getGameReadyHandler() {
        return gameReadyHandler;
    }
}
