package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import tokyo.ramune.savannacore.gun.Bullet;
import tokyo.ramune.savannacore.gun.event.BulletHitBlockEvent;
import tokyo.ramune.savannacore.gun.event.BulletHitEntityEvent;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;

public final class BulletHitListener implements Listener {
    @EventHandler
    public void onProjectiveHit(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();
        final Bullet bullet = Bullet.getBullet(event.getEntity());
        if (bullet == null) return;

        if (event.getHitBlock() != null) {
            projectile.remove();
            event.setCancelled(true);
            EventUtil.callEvent(new BulletHitBlockEvent(bullet, projectile.getLocation().getBlock()));
            return;
        }
        if (event.getHitEntity() != null) {
            EventUtil.callEvent(new BulletHitEntityEvent(bullet, event.getHitEntity(), Util.isHeadshot(event.getHitEntity(), bullet)));
        }
    }
}