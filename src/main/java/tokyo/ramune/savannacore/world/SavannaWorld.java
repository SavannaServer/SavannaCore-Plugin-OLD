package tokyo.ramune.savannacore.world;

import org.bukkit.Location;
import org.bukkit.World;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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

    void spawnObject(@Nonnull WorldObject worldObject) {
        worldObjects.add(worldObject);
        // TODO
    }

    void removeObject(@Nonnull WorldObject worldObject) {
        if (!worldObjects.contains(worldObject)) return;
        worldObject.remove();
        worldObjects.remove(worldObject);
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public WorldConfig getConfig() {
        return config;
    }

    public void reloadConfig() {
        config.reloadConfig();
        config.saveDefaultConfig();
    }

    public List<Location> getSpawnLocations() {
        final List<Map<String, Double>> spawnLocationMap = (List<Map<String, Double>>) config.getConfig().getList(Path.SPAWN_LOCATIONS.getPath());
        return Util.toLocations(getWorld(), spawnLocationMap);
    }

    public List<Map<String, Double>> getRawSpawnLocations() {
        return (List<Map<String, Double>>) config.getConfig().getList(Path.SPAWN_LOCATIONS.getPath());
    }

    public void addSpawnLocation(@Nonnull Location location) {
        final Map<String, Double> rawLocation = new HashMap<>();

        rawLocation.put("x", location.getX());
        rawLocation.put("y", location.getY());
        rawLocation.put("z", location.getZ());
        rawLocation.put("yaw", (double) location.getYaw());
        rawLocation.put("pitch", (double) location.getPitch());

        List<Map<String, Double>> rawSpawnLocations = getRawSpawnLocations();
        rawSpawnLocations.add(rawLocation);


        getConfig().getConfig().set(Path.SPAWN_LOCATIONS.getPath(), rawSpawnLocations);
        getConfig().saveConfig();
    }

    public List<WorldObject> getDefaultWorldObjects() {
        return new ArrayList<>();
        // TODO: 2023/01/24
    }

    public enum Path {
        SPAWN_LOCATIONS("spawn-locations");

        final String path;

        Path(String path) {
            this.path = path;
        }

        public String getPath() {
            return "config." + path;
        }
    }
}
