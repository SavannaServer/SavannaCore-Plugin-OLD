package tokyo.ramune.savannacore.asset;

import net.kyori.adventure.text.Component;

public enum GunAsset {
    ASSAULT_RIFLE(
            Component.text("Assault Rifle"),
            false,
            5,
            5,
            5,
            5,
            5,
            5
    );

    private final Component name;
    private final boolean gravity;
    private final double damage, maxAmmo, fireRate, reloadTime, range, speed;

    GunAsset(Component name,
             boolean gravity,
             double damage,
             double maxAmmo,
             double fireRate,
             double reloadTime,
             double range,
             double speed) {
        this.name = name;
        this.gravity = gravity;
        this.damage = damage;
        this.maxAmmo = maxAmmo;
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.range = range;
        this.speed = speed;
    }

    public Component getName() {
        return name;
    }

    public boolean isAllowGravity() {
        return gravity;
    }

    public double getDamage() {
        return damage;
    }

    public double getMaxAmmo() {
        return maxAmmo;
    }

    public double getFireRate() {
        return fireRate;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public double getRange() {
        return range;
    }

    public double getSpeed() {
        return speed;
    }
}
