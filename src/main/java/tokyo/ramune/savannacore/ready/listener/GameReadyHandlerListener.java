package tokyo.ramune.savannacore.ready.listener;

import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.ready.GameReadyHandler;

public class GameReadyHandlerListener implements Listener {
    private final GameReadyHandler gameReadyHandler = SavannaCore.getInstance().getGameServer().getGameReadyHandler();

    public GameReadyHandler getGameReadyHandler() {
        return gameReadyHandler;
    }
}
