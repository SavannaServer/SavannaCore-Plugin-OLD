package tokyo.ramune.savannacore.world;

import org.bukkit.*;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public final class WorldHandler {
    private WorldAsset loadedWorld;

    public WorldHandler() {
    }

    public void load(@Nonnull WorldAsset worldAsset) {
        World world = new WorldCreator(worldAsset.getWorldName())
                .type(WorldType.FLAT)
                .generateStructures(false)
                .createWorld();

        final List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        for (Player player : players) {
            player.teleport(Util.getSafeSpawnPoint(Util.toLocations(worldAsset.getSpawnLocations(), world), new ArrayList<>()));
        }

        unload(loadedWorld);
        loadedWorld = worldAsset;
    }

    public void unload(@Nonnull WorldAsset worldAsset) {
        if (loadedWorld == null) {
            for (Chunk loadedChunk : Bukkit.getWorlds().get(0).getLoadedChunks()) {
                Bukkit.getWorlds().get(0).unloadChunk(loadedChunk);
            }
            Bukkit.unloadWorld(Bukkit.getWorlds().get(0), false);
            return;
        }
        Bukkit.unloadWorld(worldAsset.getWorldName(), false);

        System.out.println(Bukkit.getWorlds());
    }
}
