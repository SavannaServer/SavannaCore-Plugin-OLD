package tokyo.ramune.savannacore.gun.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import tokyo.ramune.savannacore.gun.Bullet;

import javax.annotation.Nonnull;

public class ShootEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Bullet shotBullet;

    public ShootEvent(@Nonnull Player player, @Nonnull Bullet shotBullet) {
        this.player = player;
        this.shotBullet = shotBullet;
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

    public Bullet getShotBullet() {
        return shotBullet;
    }
}

