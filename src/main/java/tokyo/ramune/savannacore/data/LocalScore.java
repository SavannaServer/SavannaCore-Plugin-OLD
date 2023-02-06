package tokyo.ramune.savannacore.data;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class LocalScore {
    private final UUID playerUniqueId;
    private final Map<GlobalScore.Key, Object> scores;

    public LocalScore(@Nonnull UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
        this.scores = new HashMap<>();
    }

    public <T> T getValue(@Nonnull GlobalScore.Key key, @Nonnull Class<T> clazz) {
        return (T) (scores.containsKey(key) ? scores.get(key.getName()) : key.getDefault());
    }

    public void setValue(@Nonnull GlobalScore.Key key, Object value) {
        scores.put(key, value);
    }

    public void applyGlobal() {
        final GlobalScore globalScore = new GlobalScore(playerUniqueId);

    }
}