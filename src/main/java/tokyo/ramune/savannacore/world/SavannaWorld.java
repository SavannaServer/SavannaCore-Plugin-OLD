package tokyo.ramune.savannacore.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SavannaWorld {
    private final String name;
    private final WorldConfig config;

    public SavannaWorld(@Nonnull String name) {
        if (!new File("./" + name).exists()) throw new IllegalArgumentException("The world folder with that name doesn't exist.");
        this.name = name;
        this.config = new WorldConfig(name);
        config.saveDefaultConfig();
    }

    public String getName() {
        return name;
    }

    @Nullable
    public World getWorld() {
        return Bukkit.getWorld(name);
    }

    public WorldConfig getConfig() {
        return config;
    }

    public List<Location> getSpawnLocations() {
        final List<Map<String, Double>> spawnLocationMap = (List<Map<String, Double>>) config.getConfig().getList("config.spawn-locations");

        return Util.toLocations(getWorld(), spawnLocationMap);
    }

    public List<WorldObject> getWorldObjects() {
        return new ArrayList<>();
        // TODO: 2023/01/24
    }
}
