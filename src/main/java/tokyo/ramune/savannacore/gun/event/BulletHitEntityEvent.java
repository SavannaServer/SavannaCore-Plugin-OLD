package tokyo.ramune.savannacore.gun.event;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tokyo.ramune.savannacore.gun.Bullet;

import javax.annotation.Nonnull;

public class BulletHitEntityEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Bullet bullet;
    private final Entity entity;
    private final boolean headshot;

    public BulletHitEntityEvent(@Nonnull Bullet bullet, @Nonnull Entity entity, boolean headshot) {
        this.bullet = bullet;
        this.entity = entity;
        this.headshot = headshot;
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

    public Entity getEntity() {
        return entity;
    }

    public boolean isHeadshot() {
        return headshot;
    }
}
