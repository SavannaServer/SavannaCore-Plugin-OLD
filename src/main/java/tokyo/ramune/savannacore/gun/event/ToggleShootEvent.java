package tokyo.ramune.savannacore.gun.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public final class ToggleShootEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final boolean shooting;

    public ToggleShootEvent(@Nonnull Player player, boolean shooting) {
        this.player = player;
        this.shooting = shooting;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @Nonnull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isShooting() {
        return shooting;
    }
}
