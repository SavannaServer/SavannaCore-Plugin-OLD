package tokyo.ramune.savannacore.gamemode;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.data.LocalScore;
import tokyo.ramune.savannacore.gamemode.event.GameModeEndEvent;
import tokyo.ramune.savannacore.gamemode.event.GameModeUpdateEvent;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMode {
    private final String name;
    private final int time;
    private final Set<Player> playingPlayers = new HashSet<>();
    private final Set<LocalScore> localScores = new HashSet<>();

    private BukkitTask timerTask;
    private int currentTime;


    public GameMode(@Nonnull String name, int time) {
        this.name = name;
        if (time < 0) throw new IllegalArgumentException("Time must be positive");
        this.time = time;
    }

    public final String getName() {
        return name;
    }

    public final int getTime() {
        return time;
    }

    public final Set<Player> getPlayingPlayers() {
        return playingPlayers;
    }

    public final int getCurrentTime() {
        return currentTime;
    }

    public final void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    void onLoad() {
        startTimer();
    }

    void onUnload() {
        currentTime = 0;
        stopTimer();
        EventUtil.callEvent(new GameModeEndEvent(this));
    }

    void onUpdate() {
        EventUtil.callEvent(new GameModeUpdateEvent(this));
    }

    void onPlayerSpawn(Player player) {
        final List<Location> spawnLocations = SavannaCore.getInstance()
                .getGameServer()
                .getGameModeHandler()
                .getCurrentWorld()
                .getSpawnLocations();

        player.teleport(Util.getRandom(spawnLocations));
    }

    void onPlayerJoin(@Nonnull Player player) {
        playingPlayers.add(player);
    }

    void onPlayerQuit(@Nonnull Player player) {
        playingPlayers.remove(player);
    }

    void onPlayerDeath(@Nonnull Player player) {

        new BukkitRunnable() {
            @Override
            public void run() {
                onPlayerQuit(player);
            }
        }.runTaskLater(SavannaCore.getInstance(), 60);
    }

    public final void joinPlayer(@Nonnull Player player) {
        if (playingPlayers.contains(player)) return;
        playingPlayers.add(player);
        onPlayerJoin(player);
    }

    public final void quitPlayer(@Nonnull Player player) {
        if (!playingPlayers.contains(player)) return;
        playingPlayers.remove(player);
        onPlayerQuit(player);
    }

    public final boolean isEnded() {
        return currentTime <= 0;
    }

    private void startTimer() {
        if (timerTask != null) timerTask.cancel();
        currentTime = time;
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                currentTime--;
                if (currentTime <= 0) {
                    stopTimer();
                    onUnload();
                    return;
                }
                onUpdate();
            }
        }.runTaskTimer(SavannaCore.getInstance(), 20, 20);
    }

    private void stopTimer() {
        if (timerTask == null) return;
        currentTime = 0;
        timerTask.cancel();
        timerTask = null;
    }
}
