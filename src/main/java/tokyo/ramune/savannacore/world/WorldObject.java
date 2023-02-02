package tokyo.ramune.savannacore.world;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public final class WorldObject {
    private final SavannaWorld savannaWorld;
    private final Vector pos1, pos2;
    private final int maxHealth;
    private final Material replaceType;
    private int health;

    public WorldObject(@Nonnull SavannaWorld savannaWorld, @Nonnull Vector pos1, @Nonnull Vector pos2, int maxHealth, @Nonnull Material replaceType) {
        this.savannaWorld = savannaWorld;
        this.pos1 = pos1;
        this.pos2 = pos2;
        if (maxHealth <= 0) throw new IllegalArgumentException("maxHealth must be bigger than 0");
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.replaceType = replaceType;
    }

    public SavannaWorld getSavannaWorld() {
        return savannaWorld;
    }

    public Vector getPos1() {
        return pos1;
    }

    public Vector getPos2() {
        return pos2;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (maxHealth <= 0) throw new IllegalArgumentException("maxHealth must be bigger than 0");
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Material getReplaceType() {
        return replaceType;
    }

    public void spawn() {
    }

    public void remove() {
    }
}
