package tokyo.ramune.savannacore.gun.listener;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.gun.Bullet;
import tokyo.ramune.savannacore.gun.event.BulletMoveEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class BulletMoveEventListener implements Listener {
    Set<Bullet> bullets = new HashSet<>();
    Map<Bullet, Location> lastLocations = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onProjectileLaunch(ProjectileLaunchEvent event) {
        final SavannaCore plugin = SavannaCore.getInstance();
        new BukkitRunnable() {
            @Override
            public void run() {
                final Projectile projectile = event.getEntity();
                final Bullet bullet = Bullet.getBullet(projectile);
                if (bullet == null) return;
                bullets.add(bullet);
                lastLocations.put(bullet, projectile.getLocation());
                runMeasurementTask(bullet, projectile);
            }
        }.runTaskLater(plugin, 1);
    }

    private void runMeasurementTask(@Nonnull Bullet bullet, @Nonnull Projectile projectile) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new BulletMoveEvent(bullet, lastLocations.get(bullet), projectile.getLocation()));
                lastLocations.put(bullet, projectile.getLocation());
                if (!bullets.contains(bullet)) this.cancel();
            }
        }.runTaskTimer(SavannaCore.getInstance(), 1, 1);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onProjectileHit(ProjectileHitEvent event) {
        final Projectile projectile = event.getEntity();
        final Bullet bullet = Bullet.getBullet(projectile);
        if (bullet == null) return;
        bullets.remove(bullet);
    }

    @EventHandler
    public void onEntityRemoveFromWorld(EntityRemoveFromWorldEvent event) {
        final Entity entity = event.getEntity();
        if (!(entity instanceof Projectile)) return;
        final Bullet bullet = Bullet.getBullet((Projectile) entity);
        if (bullet == null) return;
        bullets.remove(bullet);
    }
}
