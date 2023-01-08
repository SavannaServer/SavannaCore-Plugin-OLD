package tokyo.ramune.savannacore.gun;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import net.md_5.bungee.api.chat.KeybindComponent;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.*;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.util.SoundUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public final class Bullet {
    private static final Set<Bullet> BULLETS = new HashSet<>();
    private final Class<? extends Projectile> entityType;
    private Projectile projectile;
    private Player shooter;
    private int damage = 1;
    private Location shotLocation;
    private double maxDistance = 100;
    private double distance = 0;

    public Bullet(Class<? extends Projectile> entityType) {
        this.entityType = entityType;
        BULLETS.add(this);
    }

    public static void clearBullets() {
        for (final Bullet bullet : BULLETS) {
            if (bullet.getProjectile() != null)
                bullet.getProjectile().remove();
        }
    }

    @Nullable
    public static Bullet getBullet(@Nonnull Projectile projectile) {
        return BULLETS.stream()
                .filter(bullet -> bullet.getProjectile() != null && bullet.getProjectile().equals(projectile))
                .findFirst()
                .orElse(null);
    }

    public Class<? extends Projectile> getEntityType() {
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

    @Nullable
    public Location getShotLocation() {
        return shotLocation;
    }

    public void setShotLocation(Location shotLocation) {
        this.shotLocation = shotLocation;
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

    public void shoot(@Nonnull Player shooter, @Nonnull Location location, @Nonnull Vector velocity, double shake) {
        if (projectile != null) return;
        this.shooter = shooter;
        this.shotLocation = location;
        final Random random = new Random();
        velocity.add(new Vector(random.nextDouble(-shake, shake * 2), random.nextDouble(-shake, shake * 2), random.nextDouble(-shake, shake * 2)));
        projectile = shooter.launchProjectile(entityType, velocity);
        projectile.setGravity(false);
        projectile.setVelocity(velocity);
        projectile.setSilent(true);
    }

    // Listeners

    static class BulletHitListener implements Listener {
        @EventHandler
        public void onProjectiveHit(ProjectileHitEvent event) {
            final Projectile projectile = event.getEntity();
            final Bullet bullet = getBullet(event.getEntity());
            if (bullet == null) return;

            if (event.getHitBlock() != null) {
                projectile.remove();
                final Location location = projectile.getLocation();
                final BlockData blockData = event.getHitBlock().getBlockData();
                if (bullet.getDistance() > 0) {
                    location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 5, 0, 0, 0, blockData);
                } else {
                    final Location hitBlockLocation = event.getHitBlock().getLocation();
                    location.getWorld().spawnParticle(Particle.BLOCK_CRACK, hitBlockLocation, 5, 0.1F, 0.1F, 0.1F, 0.1F, blockData);
                }
                bullet.getShooter().sendMessage(new KeybindComponent("key.swapOffhand"));
                return;
            }
            if (event.getHitEntity() != null) {
                event.setCancelled(true);
                final LivingEntity hitEntity = (LivingEntity) event.getHitEntity();
                hitEntity.playEffect(EntityEffect.HURT);
                SoundUtil.hit(bullet.getShooter());
                projectile.remove();
                double damage = hitEntity.getHealth() - bullet.damage;
                hitEntity.setHealth(damage < 0 ? 0 : damage);
            }
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent event) {
            System.out.println(1);
        }
    }

    static class BulletDistanceListener implements Listener {
        @EventHandler(priority = EventPriority.LOWEST)
        public void onBulletMove(BulletMoveEvent event) {
            final Bullet bullet = event.getBullet();
            final Location shotLocation = bullet.getShotLocation();
            if (shotLocation == null) return;

            bullet.setDistance(event.getTo().distance(shotLocation));
        }
    }

    static class BulletFarRemoveListener implements Listener {
        @EventHandler
        public void onBulletMove(BulletMoveEvent event) {
            final Bullet bullet = event.getBullet();

            if (!(bullet.getDistance() >= bullet.getMaxDistance())) return;
            if (bullet.getProjectile() == null) return;
            bullet.getProjectile().remove();
        }
    }

    static class BulletParticleListener implements Listener {
        @EventHandler
        public void onBulletMove(BulletMoveEvent event) {
            drawLine(event.getFrom(), event.getTo(), 1);
        }

        private void drawLine(Location point1, Location point2, double space) {
            World world = point1.getWorld();
            double distance = point1.distance(point2);
            Vector p1 = point1.toVector();
            Vector p2 = point2.toVector();
            Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
            double length = 0;
            for (; length < distance; p1.add(vector)) {
                world.spawnParticle(Particle.CRIT, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 0);
                length += space;
            }
        }
    }

    static class BulletMoveEventListener implements Listener {
        Set<Bullet> BULLETS = new HashSet<>();
        Map<Bullet, Location> lastLocations = new HashMap<>();

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        private void onProjectileLaunch(ProjectileLaunchEvent event) {
            final SavannaCore plugin = SavannaCore.getPlugin(SavannaCore.class);
            new BukkitRunnable() {
                @Override
                public void run() {
                    final Projectile projectile = event.getEntity();
                    final Bullet bullet = getBullet(projectile);
                    if (bullet == null) return;
                    BULLETS.add(bullet);
                    lastLocations.put(bullet, projectile.getLocation());

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getPluginManager().callEvent(new BulletMoveEvent(bullet, lastLocations.get(bullet), projectile.getLocation()));
                            lastLocations.put(bullet, projectile.getLocation());
                            if (!BULLETS.contains(bullet)) this.cancel();
                        }
                    }.runTaskTimer(plugin, 1, 1);
                }
            }.runTaskLater(plugin, 1);
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onProjectileHit(ProjectileHitEvent event) {
            final Projectile projectile = event.getEntity();
            final Bullet bullet = getBullet(projectile);
            if (bullet == null) return;
            BULLETS.remove(bullet);
        }

        @EventHandler
        public void onEntityRemoveFromWorld(EntityRemoveFromWorldEvent event) {
            final Entity entity = event.getEntity();
            if (!(entity instanceof Projectile)) return;
            final Bullet bullet = getBullet((Projectile) entity);
            if (bullet == null) return;
            BULLETS.remove(bullet);
        }
    }

    // Events

    static class BulletMoveEvent extends Event {
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
}
