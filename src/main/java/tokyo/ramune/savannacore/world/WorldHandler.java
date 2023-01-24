package tokyo.ramune.savannacore.world;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import tokyo.ramune.savannacore.utility.Util;

import javax.annotation.Nonnull;
import java.util.*;

public final class WorldHandler {
    private SavannaWorld loadedWorld;
    private final List<WorldObject> objects = new ArrayList<>();

    public WorldHandler() {
    }

    public void load(@Nonnull SavannaWorld savannaWorld) {
        final World world = new WorldCreator(savannaWorld.getName())
                .type(WorldType.FLAT)
                .generator(new EmptyChunkGenerator())
                .generateStructures(false)
                .createWorld();

        if (world == null) throw new RuntimeException("The " + savannaWorld.getName() + " Couldn't load.");

        applySettings(world);
        for (WorldObject object : savannaWorld.getWorldObjects()) {
            object.spawn(savannaWorld);
            objects.add(object);
        }
        final List<? extends Player> players = Bukkit.getOnlinePlayers().stream().toList();
        for (Player player : players) {
            player.teleport(Util.getSafeSpawnPoint(savannaWorld.getSpawnLocations(), (Collection<Player>) Bukkit.getOnlinePlayers()));
        }

        unload(loadedWorld);
        loadedWorld = savannaWorld;
    }

    public void unload(@Nonnull SavannaWorld savannaWorld) {
        if (loadedWorld == null) {
            for (Chunk loadedChunk : Bukkit.getWorlds().get(0).getLoadedChunks()) {
                Bukkit.getWorlds().get(0).unloadChunk(loadedChunk);
            }
            Bukkit.unloadWorld(Bukkit.getWorlds().get(0), false);
            return;
        }
        for (WorldObject object : objects) {
            object.remove();
        }
        Bukkit.unloadWorld(savannaWorld.getName(), false);
    }

    public void loadDefaultWorld() {
        new WorldCreator("world")
                .generator(new EmptyChunkGenerator())
                .generateStructures(false)
                .createWorld();
    }

    private void applySettings(@Nonnull World world) {
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, false);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);
        world.setGameRule(GameRule.DROWNING_DAMAGE, false);
        world.setGameRule(GameRule.FALL_DAMAGE, false);
        world.setGameRule(GameRule.FIRE_DAMAGE, false);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.MAX_COMMAND_CHAIN_LENGTH, 0);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.NATURAL_REGENERATION, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
    }

    private final static class EmptyChunkGenerator extends ChunkGenerator {

        @Override
        @Nonnull
        public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biome) {
            return createChunkData(world);
        }
    }
}
