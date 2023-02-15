package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tokyo.ramune.savannacore.gun.Bullet;
import tokyo.ramune.savannacore.gun.event.BulletMoveEvent;

public final class BulletDistanceListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBulletMove(BulletMoveEvent event) {
        final Bullet bullet = event.getBullet();
        final Location shotLocation = bullet.getLocation();
        if (shotLocation == null) return;

        bullet.setDistance(event.getTo().distance(shotLocation));
    }
}
