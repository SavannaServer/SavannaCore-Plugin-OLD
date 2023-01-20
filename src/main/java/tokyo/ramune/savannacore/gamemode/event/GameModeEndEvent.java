package tokyo.ramune.savannacore.gamemode.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tokyo.ramune.savannacore.gamemode.GameMode;

import javax.annotation.Nonnull;

public final class GameModeEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @Nonnull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final GameMode gameMode;

    public GameModeEndEvent(@Nonnull GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}