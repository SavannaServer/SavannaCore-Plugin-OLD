package tokyo.ramune.savannacore.world;

import org.bukkit.Location;
import org.bukkit.World;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SavannaWorld {
    private final World world;
    private final WorldConfig config;
    private final List<WorldObject> worldObjects = new ArrayList<>();

    SavannaWorld(@Nonnull World world) {
        if (!world.getName().startsWith("sa."))
            throw new IllegalArgumentException("The world folder name must be start with 'sa.'");
        if (!new File("./" + world.getName()).exists())
            throw new IllegalArgumentException("The world folder with that name doesn't exist.");
        this.world = world;
        this.config = new WorldConfig(world.getName());
        config.saveDefaultConfig();
    }

    public String getName() {
        return world.getName();
    }

    public World getWorld() {
        return world;
    }

    void addObject(@Nonnull WorldObject worldObject) {
        worldObjects.add(worldObject);
    }

    void removeObject(@Nonnull WorldObject worldObject) {
        if (!worldObjects.contains(worldObject)) return;
        worldObjects.remove(worldObject);
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public WorldConfig getConfig() {
        return config;
    }

    public List<Location> getSpawnLocations() {
        final List<Map<String, Double>> spawnLocationMap = (List<Map<String, Double>>) config.getConfig().getList("config.spawn-locations");

        return Util.toLocations(getWorld(), spawnLocationMap);
    }

    public List<WorldObject> getDefaultWorldObjects() {
        return new ArrayList<>();
        // TODO: 2023/01/24
    }
}
