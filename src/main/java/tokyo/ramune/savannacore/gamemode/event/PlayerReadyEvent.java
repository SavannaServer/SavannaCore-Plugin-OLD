package tokyo.ramune.savannacore.gamemode.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public final class PlayerReadyEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @Nonnull HandlerList getHandlers() {
        return HANDLERS;
    }

    public PlayerReadyEvent(@Nonnull Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}

