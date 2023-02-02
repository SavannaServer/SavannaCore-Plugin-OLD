package tokyo.ramune.savannacore.gun.event;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tokyo.ramune.savannacore.gun.Bullet;

import javax.annotation.Nonnull;

public final class BulletMoveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Bullet bullet;
    private final Location from, to;

    public BulletMoveEvent(@Nonnull Bullet bullet, @Nonnull Location from, @Nonnull Location to) {
        this.bullet = bullet;
        this.from = from;
        this.to = to;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @Nonnull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Bullet getBullet() {
        return bullet;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }
}
