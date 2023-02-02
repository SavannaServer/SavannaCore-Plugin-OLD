package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.gun.Bullet;
import tokyo.ramune.savannacore.gun.event.BulletMoveEvent;

public final class BulletFarRemoveListener implements Listener {
    @EventHandler
    public void onBulletMove(BulletMoveEvent event) {
        final Bullet bullet = event.getBullet();

        if (!(bullet.getDistance() >= bullet.getMaxDistance())) return;
        if (bullet.getProjectile() == null) return;
        bullet.getProjectile().remove();
    }
}
