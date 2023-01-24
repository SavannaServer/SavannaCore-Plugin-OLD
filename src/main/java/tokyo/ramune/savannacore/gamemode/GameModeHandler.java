package tokyo.ramune.savannacore.gamemode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.event.GameModeEndEvent;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.util.*;

public final class GameModeHandler {
    private final Set<GameMode> gameModes;
    private GameMode currentGameMode;

    public GameModeHandler(@Nonnull Set<GameMode> gameModes) {
        if (gameModes.isEmpty()) throw new IllegalArgumentException("GameModes must be not empty.");
        this.gameModes = gameModes;

        EventUtil.register(
                SavannaCore.getInstance(),
                new GameModeEndListener()
        );

        // Detect first game mode
        final List<GameMode> gameModeList = gameModes.stream().toList();
        setGameMode(Util.getRandom(gameModeList));
    }

    public void setGameMode(@Nonnull GameMode gameMode) {
        if (currentGameMode != null && !currentGameMode.isEnded()) {
            currentGameMode.onUnload();
        }
        currentGameMode = gameMode;
        loadGameMode(gameMode);
    }

    private void loadGameMode(@Nonnull GameMode gameMode) {
        gameMode.onLoad();
    }

    private final class GameModeEndListener implements Listener {
        @EventHandler
        public void onGameModeEnd(GameModeEndEvent event) {
            final GameMode gameMode = event.getGameMode();

            if (!currentGameMode.equals(gameMode)) return;
            if (currentGameMode.getClass().equals(GameOverPhase.class)) return;
            setGameMode(new GameOverPhase(gameModes));
        }
    }
}