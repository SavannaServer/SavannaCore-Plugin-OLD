package tokyo.ramune.savannacore.gamemode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.event.GameModeEndEvent;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;
import tokyo.ramune.savannacore.world.SavannaWorld;

import javax.annotation.Nonnull;
import java.util.*;

public final class GameModeHandler {
    private final List<Class<GameMode>> ignoreGameModes;
    private GameMode currentGameMode;

    public GameModeHandler(@Nonnull List<Class<GameMode>> ignoreGameModes) {
        this.ignoreGameModes = ignoreGameModes;

        EventUtil.register(
                SavannaCore.getInstance(),
                new GameModeEndListener()
        );

        // Detect first game mode
        final List<GameMode> gameModeList = getNormalGameModes().stream()
                .filter(gameMode -> !ignoreGameModes.contains(gameMode.getClass()))
                .toList();
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

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public List<GameMode> getNormalGameModes() {
        return Arrays.asList(
                new FreeForAll(getRandomWorld())
        );
    }

    private SavannaWorld getRandomWorld() {
        return Util.getRandom(SavannaCore.getInstance().getWorldHandler().getWorlds());
    }

    private final class GameModeEndListener implements Listener {
        @EventHandler
        public void onGameModeEnd(GameModeEndEvent event) {
            final GameMode gameMode = event.getGameMode();

            if (!currentGameMode.equals(gameMode)) return;
            if (currentGameMode.getClass().equals(GameOverPhase.class)) return;
            final Set<GameMode> gameModes = new HashSet<>();
            while (gameModes.size() <= 4) {
                gameModes.add(
                        Util.getRandom(
                                getNormalGameModes().stream()
                                .filter(gm -> !ignoreGameModes.contains(gm.getClass()))
                                .toList()
                        )
                );
            }
            setGameMode(new GameOverPhase(gameModes));
        }
    }
}