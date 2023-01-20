package tokyo.ramune.savannacore.gamemode;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gamemode.event.GameModeEndEvent;
import tokyo.ramune.savannacore.gamemode.event.GameModeStartEvent;
import tokyo.ramune.savannacore.gamemode.event.GameModeUpdateEvent;
import tokyo.ramune.savannacore.utility.EventUtil;

import javax.annotation.Nonnull;

public class GameMode {
    private final String name;
    private final int time;

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

    public final int getCurrentTime() {
        return currentTime;
    }

    public final void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public void onLoad() {
        startTimer();
        EventUtil.callEvent(new GameModeStartEvent(this));
    }

    public void onUnload() {
        EventUtil.callEvent(new GameModeEndEvent(this));
    }

    public void onUpdate() {
        EventUtil.callEvent(new GameModeUpdateEvent(this));
    }

    public void onJoin(@Nonnull Player player) {
    }

    public void onQuit(@Nonnull Player player) {
    }

    public boolean isEnded() {
        return currentTime <= 0;
    }

    private void startTimer() {
        if (timerTask != null) timerTask.cancel();
        currentTime = time;
        timerTask = new BukkitRunnable() {
            @Override
            public void run() {
                currentTime--;
                if (currentTime < 0) {
                    cancel();
                    onUnload();
                    return;
                }
                onUpdate();
            }
        }.runTaskTimer(SavannaCore.getPlugin(SavannaCore.class), 20, 20);
    }
}
