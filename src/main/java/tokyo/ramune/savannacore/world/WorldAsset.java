package tokyo.ramune.savannacore.world;

import org.bukkit.util.Vector;

import java.util.Collection;

public interface WorldAsset {
    String getWorldName();
    Collection<Vector> getSpawnLocations();
    Collection<WorldObject> getWorldObjects();
}
