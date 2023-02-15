package tokyo.ramune.savannacore.world;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import tokyo.ramune.savannacore.SavannaCore;
import tokyo.ramune.savannacore.utility.EventUtil;
import tokyo.ramune.savannacore.utility.Util;
import tokyo.ramune.savannacore.world.listener.DisableWorldOutListener;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.*;

public final class WorldHandler {
    public WorldHandler() {
        // Optimize default worlds
        for (World world : Bukkit.getWorlds()) {
            applySettings(world);
            for (Entity entity : world.getEntities()) {
                try {
                    entity.remove();
                } catch (Exception ignored) {
                }
            }
            for (Chunk chunk : world.getLoadedChunks()) {
                world.unloadChunk(chunk);
            }
        }

        EventUtil.register(
                SavannaCore.getInstance(),
                new DisableWorldOutListener(this)
        );

        // Load vote world
        if (!new File("sa.vote").exists()) {
            SavannaCore.getInstance().getLogger().warning("Couldn't find sa.vote world!");
            Bukkit.shutdown();
            return;
        }
        load("sa.vote");

        // Load ready world
        if (!new File("sa.ready").exists()) {
            SavannaCore.getInstance().getLogger().warning("Couldn't find sa.ready world!");
            Bukkit.shutdown();
            return;
        }
        load("sa.vote");

        // Load savanna game worlds
        for (String worldName : getWorldNames()) {
            load(worldName);
        }
    }

    public SavannaWorld get(@Nonnull String name) {
        return load(name);
    }

    public SavannaWorld load(@Nonnull String name) {
        if (!name.startsWith("sa.")) throw new IllegalArgumentException("SavannaWorld name must be start with 'sa.'");
        final World loadedWorld = Bukkit.getWorld(name);
        if (loadedWorld != null) return new SavannaWorld(loadedWorld);

        final World world = new WorldCreator(name)
                .generator(new EmptyChunkGenerator())
                .generateStructures(false)
                .createWorld();

        if (world == null) throw new RuntimeException("The world " + name + " Couldn't load.");

        final SavannaWorld savannaWorld = new SavannaWorld(world);

        applySettings(world);
        for (WorldObject object : savannaWorld.getDefaultWorldObjects()) {
            savannaWorld.spawnObject(object);
        }
        final List<? extends Player> players = Bukkit.getOnlinePlayers().stream().toList();
        for (Player player : players) {
            player.teleport(Util.getSafeSpawnPoint(savannaWorld.getSpawnLocations(), (Collection<Player>) Bukkit.getOnlinePlayers()));
        }

        return savannaWorld;
    }

    private void applySettings(@Nonnull World world) {
        world.setAutoSave(false);
        world.setViewDistance(7);
        world.setDifficulty(Difficulty.EASY);
        world.setHardcore(false);
        world.setKeepSpawnInMemory(false);
        world.setSendViewDistance(7);
        world.setSimulationDistance(7);
        world.setSpawnFlags(false, false);
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

    public List<String> getWorldNames() {
        final List<String> worlds = new ArrayList<>();

        for (File file : Objects.requireNonNull(new File("./").listFiles())) {
            if (!file.getName().startsWith("sa.")) continue;
            if (file.getName().equals("sa.vote") || file.getName().equals("sa.lobby") || file.getName().equals("sa.ready"))
                continue;
            worlds.add(file.getName());
        }
        return worlds;
    }

    public final static class EmptyChunkGenerator extends ChunkGenerator {
        @Override
        @Nonnull
        public ChunkData generateChunkData(@Nonnull World world, @Nonnull Random random, int x, int z, @Nonnull BiomeGrid biome) {
            return createChunkData(world);
        }
    }
}
