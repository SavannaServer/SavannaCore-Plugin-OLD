package tokyo.ramune.savannacore.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.*;

public final class Util {
    public static List<Location> toLocations(@Nonnull World world, @Nonnull List<Map<String, Double>> rawLocations) {
        final List<Location> locations = new ArrayList<>();
        for (Map<String, Double> raw : rawLocations) {
            locations.add(new Location(world, raw.get("x"), raw.get("y"), raw.get("z"), raw.get("yaw").floatValue(), raw.get("pitch").floatValue()));
        }
        return locations;
    }

    public static void teleport(@Nonnull Location location) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(location);
        }
    }

    public static String formatElapsedTime(int secs) {
        if (secs <= 0) return "0";
        int hours = secs / 3600;
        int minutes = (secs % 3600) / 60;
        int seconds = secs % 60;

        if (hours == 0 && minutes == 0) return String.valueOf(seconds);
        else if (hours == 0) return minutes + ":" + seconds;
        else return hours + ":" + minutes + ":" + seconds;
    }

    public static Location toCenterLocation(@Nonnull Location location) {
        final World world = location.getWorld();
        final double x = location.getBlockX(),
                y = location.getBlockY(),
                z = location.getBlockZ();
        return new Location(world, x + 0.5, y, z + 0.5);
    }

    public static <T> T getRandom(Collection<T> collection) {
        final List<T> list = new ArrayList<>(collection);
        final Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    public static Location getSafeSpawnPoint(@Nonnull Collection<Location> spawnPoints, @Nonnull Collection<Player> avoidPlayers) {
        if (spawnPoints.isEmpty()) return new Location(Bukkit.getWorlds().get(1), 0, 0, 0);

        final Map<Location, Double> playersDistances = new HashMap<>();

        for (Location spawnPoint : spawnPoints) {
            double distances = 0.0;
            for (Player avoidPlayer : avoidPlayers) {
                try {
                    distances += spawnPoint.distance(avoidPlayer.getLocation());
                } catch (Exception ignored) {
                }
            }
            playersDistances.put(spawnPoint, distances);
        }
        final Map.Entry<Location, Double> maxEntry = Collections.max(playersDistances.entrySet(), Map.Entry.comparingByValue());

        return maxEntry.getKey();
    }

    public static List<Location> toLocations(@Nonnull Collection<Vector> vectors, @Nonnull World world) {
        final List<Location> locations = new ArrayList<>();
        for (Vector vector : vectors) {
            locations.add(vector.toLocation(world));
        }
        return locations;
    }

    public static <T> T getVoteResult(@Nonnull Map<T, Integer> gameModeVotes) {
        if (gameModeVotes.isEmpty()) throw new IllegalArgumentException("GameModeVotes must be non empty.");

        final int maxVoteCount = Collections.max(gameModeVotes.entrySet(), Map.Entry.comparingByValue()).getValue();
        final List<T> values = new ArrayList<>();

        for (Map.Entry<T, Integer> entry : gameModeVotes.entrySet()) {
            if (entry.getValue() != maxVoteCount) continue;
            values.add(entry.getKey());
        }
        if (values.size() == 1) return values.get(0);
        return Util.getRandom(values);
    }
}
