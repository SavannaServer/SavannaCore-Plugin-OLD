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

    public long getValue(@Nonnull GlobalScore.Key key) {
        return (long) (scores.containsKey(key.getName()) ? scores.get(key.getName()) : key.getDefault());
    }

    public void setValue(@Nonnull GlobalScore.Key key, long value) {
        scores.put(key, value);
    }

    public void applyGlobal() {
        final GlobalScore globalScore = new GlobalScore(playerUniqueId);
        for (Map.Entry<GlobalScore.Key, Object> entry : scores.entrySet()) {
            globalScore.setValue(entry.getKey(), entry.getKey().add(globalScore.getValue(entry.getKey()), entry.getValue()));
        }
        globalScore.save();
    }
}