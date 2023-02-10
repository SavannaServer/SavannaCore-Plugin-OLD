package tokyo.ramune.savannacore.gun.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import tokyo.ramune.savannacore.gun.Bullet;

public class BulletDamageListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();

        if (!(damager instanceof Projectile)) return;
        final Bullet bullet = Bullet.getBullet((Projectile) damager);

        if (bullet == null) return;
        event.setDamage(bullet.getDamage());
    }
}
