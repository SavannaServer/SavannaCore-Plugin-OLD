package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Bullet {
    private static final List<Bullet> bullets = new ArrayList<>();
    private final Class<? extends ThrowableProjectile> entityType;
    private Projectile projectile = null;
    private Player shooter;
    private int damage = 1;
    private double maxDistance = 10;
    private double distance = 0;
    public Bullet(Class<? extends ThrowableProjectile> entityType) {
        this.entityType = entityType;
        bullets.add(this);
    }

    public static void clearBullets() {
        for (final Bullet bullet : bullets) {
            if (bullet.getProjectile() != null)
                bullet.getProjectile().remove();
        }
    }

    @Nullable
    public static Bullet getBullet(@Nonnull Entity projectile) {
        return bullets.stream()
                .filter(bullet -> bullet.getProjectile() != null && bullet.getProjectile().equals(projectile))
                .findFirst()
                .orElse(null);
    }

    public Class<? extends ThrowableProjectile> getEntityType() {
        return entityType;
    }

    @Nullable
    public Projectile getProjectile() {
        return projectile;
    }

    public Player getShooter() {
        return shooter;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void shoot(@Nonnull Player shooter, @Nonnull Location location, @Nonnull Vector velocity) {
        this.shooter = shooter;
        projectile = location.getWorld().spawn(location, entityType);
        projectile.setVelocity(velocity);
    }

    static class BulletHitListener implements Listener {
        @EventHandler
        public void onProjectiveHit(ProjectileHitEvent event) {
            final Entity entity = event.getEntity();
            final Bullet bullet = getBullet(entity);
            if (bullet == null) return;

            if (event.getHitBlock() != null) {
                event.setCancelled(true);
                entity.remove();

                final Location location = entity.getLocation();
                location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 5, 0, 0, 0, event.getHitBlock().getBlockData());
                return;
            }
            if (event.getHitEntity() != null) {
                if (!(event.getHitEntity() instanceof LivingEntity)) {
                    event.setCancelled(true);
                    return;
                }
                final LivingEntity hitEntity = (LivingEntity) event.getHitEntity();
                if (hitEntity.equals(bullet.getShooter())) {
                    event.setCancelled(true);
                    return;
                }

                try {
                    hitEntity.setHealth(hitEntity.getHealth() - bullet.damage);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
