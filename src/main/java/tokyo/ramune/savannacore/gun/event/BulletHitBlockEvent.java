package tokyo.ramune.savannacore.gun.event;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tokyo.ramune.savannacore.gun.Bullet;

import javax.annotation.Nonnull;

public final class BulletHitBlockEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Bullet bullet;
    private final Block hitBlock;

    public BulletHitBlockEvent(@Nonnull Bullet bullet, @Nonnull Block hitBlock) {
        this.bullet = bullet;
        this.hitBlock = hitBlock;
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

    public Block getHitBlock() {
        return hitBlock;
    }
}
