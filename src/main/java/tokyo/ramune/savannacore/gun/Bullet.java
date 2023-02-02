package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.asset.SoundAsset;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public final class Bullet {
    private static final Set<Bullet> bullets = new HashSet<>();
    private final Class<? extends Projectile> entityType;
    private @Nullable Projectile projectile;
    private Player shooter;
    private int damage = 1;
    private @Nullable Location shotLocation;
    private boolean gravity = false;
    private double shake = 0.1;
    private double maxDistance = 100;
    private double distance = 0;
    public Bullet(Class<? extends Projectile> entityType) {
        this.entityType = entityType;
    }

    public static void clearBullets() {
        for (final Bullet bullet : bullets) {
            if (bullet.getProjectile() != null)
                bullet.getProjectile().remove();
        }
    }

    @Nullable
    public static Bullet getBullet(@Nonnull Projectile projectile) {
        return bullets.stream()
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

    public void setShotLocation(@Nullable Location shotLocation) {
        this.shotLocation = shotLocation;
    }

    public boolean hasGravity() {
        return gravity;
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
    }

    public double getShake() {
        return shake;
    }

    public void setShake(double shake) {
        this.shake = shake;
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
        if (projectile != null) return;
        this.shooter = shooter;
        this.shotLocation = location;
        final Random random = new Random();
        velocity.add(new Vector(random.nextDouble(-shake, shake * 2), random.nextDouble(-shake, shake * 2), random.nextDouble(-shake, shake * 2)));
        projectile = shooter.launchProjectile(entityType, velocity);
        projectile.setGravity(gravity);
        projectile.setVelocity(velocity);
        projectile.setSilent(true);

        SoundAsset.SHOOT.play(shotLocation);

        bullets.add(this);
    }
}
