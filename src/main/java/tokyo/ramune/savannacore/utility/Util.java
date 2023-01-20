package tokyo.ramune.savannacore.utility;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.gamemode.GameMode;

import javax.annotation.Nonnull;
import java.util.*;

public final class Util {
    public static <T> T getRandom(List<T> list) {
        final Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    public static Location getSafeSpawnPoint(@Nonnull Set<Location> spawnPoints, @Nonnull Set<Player> avoidPlayers) {
        final Map<Location, Double> playersDistances = new HashMap<>();

        for (Location spawnPoint : spawnPoints) {
            double distances = 0.0;
            for (Player avoidPlayer : avoidPlayers) {
                distances += spawnPoint.distance(avoidPlayer.getLocation());
            }
            playersDistances.put(spawnPoint, distances);
        }
        final Map.Entry<Location, Double> maxEntry = Collections.max(playersDistances.entrySet(), Map.Entry.comparingByValue());

        return maxEntry.getKey();
    }

    public static GameMode detectGameMode(@Nonnull Map<GameMode, Integer> gameModeVotes) {
        if (gameModeVotes.isEmpty()) throw new IllegalArgumentException("GameModeVotes must be non empty.");

        final int maxVoteCount = Collections.max(gameModeVotes.entrySet(), Map.Entry.comparingByValue()).getValue();
        final List<GameMode> gameModes = new ArrayList<>();

        for (Map.Entry<GameMode, Integer> gameModeIntegerEntry : gameModeVotes.entrySet()) {
            if (gameModeIntegerEntry.getValue() != maxVoteCount) continue;
            gameModes.add(gameModeIntegerEntry.getKey());
        }
        if (gameModes.size() == 1) return gameModes.get(0);
        return Util.getRandom(gameModes);
    }
}
