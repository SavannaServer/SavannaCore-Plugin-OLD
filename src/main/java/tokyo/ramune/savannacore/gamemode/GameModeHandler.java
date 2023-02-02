package tokyo.ramune.savannacore.gamemode;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.event.GameModeEndEvent;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;
import tokyo.ramune.savannacore.world.SavannaWorld;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GameModeHandler {
    private final List<Class<GameMode>> ignoreGameModes;
    private GameMode currentGameMode;
    private SavannaWorld world;

    public GameModeHandler(@Nonnull List<Class<GameMode>> ignoreGameModes) {
        this.ignoreGameModes = ignoreGameModes;

        EventUtil.register(
                SavannaCore.getInstance(),
                new GameModeEndListener()
        );

        // Detect first game mode
        final List<String> savannaWorlds = SavannaCore.getInstance().getWorldHandler().getWorldNames();
        if (savannaWorlds.size() == 0) {
            SavannaCore.getInstance().getLogger().warning("Savanna world couldn't found.");
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                loadGameMode(Util.getRandom(getNormalGameModes()), Util.getRandom(savannaWorlds));
            }
        }.runTaskLater(SavannaCore.getInstance(), 10);
    }

    void loadGameMode(@Nonnull GameMode gameMode, @Nonnull String worldName) {
        if (currentGameMode != null && !currentGameMode.isEnded()) {
            currentGameMode.onUnload();
        }
        world = SavannaCore.getInstance().getWorldHandler().load(worldName);
        currentGameMode = gameMode;
        gameMode.onLoad();
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public List<GameMode> getNormalGameModes() {
        return List.of(
                new FreeForAll()
        );
    }

    public SavannaWorld getWorld() {
        return world;
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
            loadGameMode(new GameOverPhase(), "sa.vote");
        }
    }
}