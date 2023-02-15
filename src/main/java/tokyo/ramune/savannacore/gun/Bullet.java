package tokyo.ramune.savannacore.gun;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.asset.BulletParticleAsset;
import tokyo.ramune.savannacore.asset.SoundAsset;
import tokyo.ramune.savannacore.gun.event.ShootEvent;
import tokyo.ramune.savannacore.gun.listener.*;
import tokyo.ramune.savannacore.utility.EventUtil;

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
    private BulletParticleAsset particle = BulletParticleAsset.ASH;
    private int damage = 1;
    private @Nullable Location location;
    private boolean gravity = false, visible = false;
    private double velocity = 3, range = 0.1, maxDistance = 100, distance = 0;

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

    public static void registerListener() {
        EventUtil.register(
                SavannaCore.getInstance(),
                new BulletBlockCrackListener(),
                new BulletDamageListener(),
                new BulletDistanceListener(),
                new BulletFarRemoveListener(),
                new BulletHitListener(),
                new BulletHitSoundListener(),
                new BulletMoveEventListener(),
                new BulletParticleListener()
        );
    }

    public Class<? extends Projectile> getEntityType() {
        return entityType;
    }

    @Nullable
    public Projectile getProjectile() {
        return projectile;
    }

    @Nullable
    public Player getShooter() {
        return shooter;
    }

    public BulletParticleAsset getParticle() {
        return particle;
    }

    public Bullet setParticle(BulletParticleAsset particle) {
        this.particle = particle;
        return this;
    }

    public int getDamage() {
        return damage;
    }

    public Bullet setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    public Bullet setLocation(@Nullable Location location) {
        this.location = location;
        return this;
    }

    public boolean hasGravity() {
        return gravity;
    }

    public double getVelocity() {
        return velocity;
    }

    public Bullet setVelocity(double velocity) {
        this.velocity = velocity;
        return this;
    }

    public double getRange() {
        return range;
    }

    public Bullet setRange(double range) {
        this.range = range;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public Bullet setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public boolean isGravity() {
        return gravity;
    }

    public Bullet setGravity(boolean gravity) {
        this.gravity = gravity;
        return this;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public Bullet setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public Bullet setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Bullet shoot(@Nonnull Player shooter) {
        if (projectile != null) return this;
        this.shooter = shooter;
        this.location = shooter.getLocation().add(0, 1.5, 0);
        final Vector velocity = shooter.getLocation().getDirection().multiply(this.velocity);
        final Random random = new Random();

        velocity.add(new Vector(random.nextDouble(-range, range), random.nextDouble(-range, range), random.nextDouble(-range, range)));
        projectile = shooter.launchProjectile(entityType, velocity);
        projectile.setGravity(gravity);
        projectile.setVelocity(velocity);
        projectile.setSilent(true);
        SoundAsset.SHOOT.play(location);

        bullets.add(this);

        EventUtil.callEvent(new ShootEvent(shooter, this));
        return this;
    }
}
