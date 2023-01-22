package tokyo.ramune.savannacore.asset;

import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.world.WorldAsset;
import tokyo.ramune.savannacore.world.WorldObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum NormalWorld implements WorldAsset {
    TEST("test", List.of(new Vector(0, -60, 0)), new ArrayList<>());

    private final String worldName;
    private final Collection<Vector> spawnLocations;
    private final Collection<WorldObject> worldObjects;

    NormalWorld(String worldName, Collection<Vector> spawnLocations, Collection<WorldObject> worldObjects) {
        this.worldName = worldName;
        this.spawnLocations = spawnLocations;
        this.worldObjects = worldObjects;
    }

    @Override
    public String getWorldName() {
        return worldName;
    }

    @Override
    public Collection<Vector> getSpawnLocations() {
        return spawnLocations;
    }

    @Override
    public Collection<WorldObject> getWorldObjects() {
        return worldObjects;
    }
}
