package tokyo.ramune.savannacore.world;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class WorldObject {
    private final Vector pos1, pos2;
    private final WorldObjectEntityCreator<? extends Entity> entityCreator;
    private final int maxHealth;
    private int health;
    private final Material replaceType;

    private Entity spawnedEntity;

    public WorldObject(@Nonnull Vector pos1, @Nonnull Vector pos2, @Nullable WorldObjectEntityCreator<? extends Entity> entityCreator, int maxHealth, @Nonnull Material replaceType) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.entityCreator = entityCreator;
        if (maxHealth <= 0) throw new IllegalArgumentException("maxHealth must be bigger than 0");
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.replaceType = replaceType;
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

    public void spawn(@Nonnull SavannaWorld savannaWorld) {
        if (entityCreator == null) return;

        final World world = Bukkit.getWorld(savannaWorld.getName());
        if (world == null) return;
        spawnedEntity = world.spawn(entityCreator.getLocation().toLocation(world), entityCreator.getClazz());
    }

    public void remove() {
        if (spawnedEntity == null) return;
        spawnedEntity.remove();
    }
}
